package com.example.foodbookbd;

import android.text.SpannableString;

public class CLVRow {
	private SpannableString headline;
	private Integer intCLV;

	public CLVRow(Integer intCLV,SpannableString headline) {
		this.intCLV=intCLV;
		this.headline=headline;
	}
	
	public Integer getIntCLV() {
		return intCLV;
	}

	public void setIvCLV(Integer intCLV) {
		this.intCLV = intCLV;
	}

	public SpannableString getHeadline() {
		return headline;
	}

	public void setHeadline(SpannableString headline) {
		this.headline = headline;
	}

}
