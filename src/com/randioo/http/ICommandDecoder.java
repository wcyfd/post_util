package com.randioo.http;

public interface ICommandDecoder {
	void decode(String cmd);

	PostData execute();
}
