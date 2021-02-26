package com.healthplus.healthchamp.Activity;

public interface AuthConstants {

	// TODO Change it to your web domain
	public final static String WEB_DOMAIN = "zoom.us";
	// TODO Change it to your APP Key
	public final static String SDK_KEY = "3807r2XBhTsXL4oTy4gyO1lrMvcK6j5PNldQ";

	// TODO Change it to your APP Secret
	public final static String SDK_SECRET = "4ZiRyxplma3DCW24ODPwXgWuMJ619Vt43nwS";
	/**
	 * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
	 * We hardcore it here, just to run the demo.
	 *
	 * You can generate a jwttoken on the https://jwt.io/
	 * with this payload:
	 * {
	 *
	 *     "appKey": "string", // app key
	 *     "iat": long, // access token issue timestamp
	 *     "exp": long, // access token expire time
	 *     "tokenExp": long // token expire time
	 * }
	 */
	public final static String SDK_JWTTOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBLZXkiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyMzkwMjIsInRva2VuRXhwIjoxNTE2MjM5MDIyfQ.U-joe42oXwCJEA2ypGO3ptPBtLyRiYOjl5g-8scDkgU";

}
