package com.opstty;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class DoubleIntWritable implements Writable {
	private IntWritable val1;
	private IntWritable val2;

	public DoubleIntWritable() {
		this.val1 = new IntWritable(0);
		this.val2 = new IntWritable(0);
	}
	
	public DoubleIntWritable(IntWritable v1, IntWritable v2) {
		this.val1 = v1;
		this.val2 = v2;
	}

	public IntWritable getVal1() {
		return val1;
	}

	public IntWritable getVal2() {
		return val2;
	}

	public void setVal1(IntWritable v1) {
		this.val1 = v1;
	}

	public void setVal2(IntWritable v2) {
		this.val2 = v2;
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		val1.readFields(arg0);
		val2.readFields(arg0);
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		val1.write(arg0);
		val2.write(arg0);
	}
	
	@Override
	public boolean equals(Object o) {
		DoubleIntWritable diw = (DoubleIntWritable) o;
		if (this.val1.get() == diw.getVal1().get() &&
				this.val2.get() == diw.getVal2().get()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return val1.toString() + "\t" + val2.toString();
	}
}
