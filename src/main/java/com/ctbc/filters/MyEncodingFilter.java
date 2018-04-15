package com.ctbc.filters;

import org.springframework.web.filter.CharacterEncodingFilter;

public class MyEncodingFilter extends CharacterEncodingFilter {

	public MyEncodingFilter() {
		super();
		System.out.println("============= MyEncodingFilter 建立 ==============");
	}

	public MyEncodingFilter(String encoding, boolean forceRequestEncoding, boolean forceResponseEncoding) {
		super(encoding, forceRequestEncoding, forceResponseEncoding);
		// TODO Auto-generated constructor stub
	}

	public MyEncodingFilter(String encoding, boolean forceEncoding) {
		super(encoding, forceEncoding);
		// TODO Auto-generated constructor stub
	}

	public MyEncodingFilter(String encoding) {
		super(encoding);
		// TODO Auto-generated constructor stub
	}

}
