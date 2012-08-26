package com.tida.servir.components;

import org.apache.tapestry5.MarkupWriter;

public class Envelope {
	private String contents = "";
	
	public void setContents(String contents) {
		this.contents = "<div id='ok_msg'>"+ contents + "</div>";
	}
	
	public String getContents() { 
		return contents;
	}
	
	private void beginRender(MarkupWriter writer) { 
		writer.writeRaw(contents);
	}
}
