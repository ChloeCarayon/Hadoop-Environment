package com.opstty;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class DistrictYearWritable implements Writable {
	private IntWritable district;
	private IntWritable year;

	public DistrictYearWritable() {
		this.district = new IntWritable(0);
		this.year = new IntWritable(0);
	}
	
	public DistrictYearWritable(IntWritable district, IntWritable year) {
		this.district = district;
		this.year = year;
	}

	public IntWritable getDistrict() {
		return district;
	}

	public IntWritable getYear() {
		return year;
	}

	public void setDistrict(IntWritable district) {
		this.district = district;
	}

	public void setYear(IntWritable year) {
		this.year = year;
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		district.readFields(arg0);
		year.readFields(arg0);
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		district.write(arg0);
		year.write(arg0);
	}

	@Override
	public String toString() {
		return district.toString() + "\t" + year.toString();
	}
}
