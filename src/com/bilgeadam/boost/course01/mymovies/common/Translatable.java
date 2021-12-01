package com.bilgeadam.boost.course01.mymovies.common;

/**
 * @author $Görkem Sönmez
 */
public interface Translatable {
	
	String getI18NKey();
	
	default String getI18NKeyIdentifier() {
		return this.getClass().getSimpleName().toUpperCase() + ".";
	}
}
