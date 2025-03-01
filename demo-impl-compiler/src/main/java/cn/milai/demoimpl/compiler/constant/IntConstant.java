package cn.milai.demoimpl.compiler.constant;

import cn.milai.common.base.Bytes;

/**
 * int 类型常量
 * 2020.01.01
 * @author milai
 */
public class IntConstant extends Constant<Integer> {

	public IntConstant(Integer value) {
		super(value);
	}

	@Override
	public byte[] getBytes() { return Bytes.fromInt32(value); }

	@Override
	public ConstantType getType() { return ConstantType.INT; }
}
