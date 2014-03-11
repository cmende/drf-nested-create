package com.example;

import com.example.controller.HttpConnector;
import com.example.model.Bar;
import com.example.model.Foo;

public class Main {
	public static void main(String[] args) {
		HttpConnector con = new HttpConnector();
		Bar[] bars = con.getBars();
		Foo foo = new Foo();
		foo.setBar(bars[0]);
		foo.setName(String.valueOf(Math.random()));
		HttpConnector.Response response = con.addOrUpdateFoo(foo);
		if (response.getCode() >= 400) {
			System.err.println(response.getBody());
		} else {
			System.out.println(response.getBody());
		}
	}
}
