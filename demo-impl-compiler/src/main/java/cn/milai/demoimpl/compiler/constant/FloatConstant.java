package cn.milai.demoimpl.compiler.constant;

import cn.milai.common.base.Bytes;

/**
 * float 类型常量
 * 2020.01.01
 * @author milai
 */
public class FloatConstant extends Constant<Float> {

	public FloatConstant(Float value) {
		super(value);
	}

	@Override
	public ConstantType getType() { return ConstantType.FLOAT; }

	@Override
	public byte[] getBytes() { return Bytes.fromInt32(Float.floatToRawIntBits(value)); }

}
