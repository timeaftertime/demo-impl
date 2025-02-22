package cn.milai.demoimpl.compiler;

public class CompilerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompilerException(Throwable e) {
		super(e);
	}

	public CompilerException(String msg) {
		super(msg);
	}

	public CompilerException(String msg, Throwable e) {
		super(msg, e);
	}
}
