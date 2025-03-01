package cn.milai.demoimpl.compiler.constant;

import cn.milai.common.base.Bytes;

/**
 * long 类型常量
 * 2020.01.04
 * @author milai
 */
public class LongConstant extends Constant<Long> {

	public LongConstant(Long value) {
		super(value);
	}

	@Override
	public ConstantType getType() { return ConstantType.LONG; }

	@Override
	public byte[] getBytes() { return Bytes.fromInt64(value); }

}
