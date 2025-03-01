package cn.milai.demoimpl.compiler.frontend.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.milai.common.collection.Creator;
import cn.milai.common.collection.Filter;

/**
 * 用于输出最终生成的语法推导式的工具类
 * 
 * @author milai
 * @date 2020.04.16
 */
public class GrammerPrintUtil {

	public static List<String> convert(InputStream input) throws IOException {
		Grammer grammer = GrammerReader.parseGrammer(input);
		Map<String, String> alias = grammer.getAlias();
		Set<NonTerminalSymbol> nonTerminals = grammer.getNonTerminals();
		NonTerminalSymbol cfg = Filter.first(nonTerminals, s -> s.getCode().equals(Keywords.CFG)).get();
		Set<Symbol> visited = Creator.hashSet(cfg);
		List<String> output = new ArrayList<>();
		writeNode(visited, output, cfg, reverseAlias(alias));
		return output;
	}

	private static Map<String, String> reverseAlias(Map<String, String> alias) {
		Map<String, String> res = new HashMap<>();
		for (String key : alias.keySet()) {
			res.put(alias.get(key), key);
		}

		return res;
	}

	private static void writeNode(Set<Symbol> visited, List<String> out, NonTerminalSymbol s, Map<String, String> alias)
			throws IOException {
		Queue<NonTerminalSymbol> q = new ConcurrentLinkedQueue<>();
		boolean hasEpsilonProdution = false;
		for (Production p : s.getProductions()) {
			StringBuilder sb = new StringBuilder(s + " " + Keywords.PRODUCTION);
			if (p.isEpsilon()) {
				hasEpsilonProdution = true;
				continue;
			} else {
				for (Symbol r : p.getRights()) {
					sb.append(" " + alias.getOrDefault(r.getCode(), r.getCode()));
					if (r.isNonTerminal() && visited.add(r)) {
						q.add((NonTerminalSymbol) r);
					}
				}
			}
			out.add(sb.toString() + System.lineSeparator());
		}
		if (hasEpsilonProdution) {
			out.add(s + " " + Keywords.PRODUCTION + " " + Symbol.EPSILON + System.lineSeparator());
		}
		out.add(System.lineSeparator());
		while (!q.isEmpty()) {
			writeNode(visited, out, q.poll(), alias);
		}
	}

}
