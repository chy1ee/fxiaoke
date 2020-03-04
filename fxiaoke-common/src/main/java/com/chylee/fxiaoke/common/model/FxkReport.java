package com.chylee.fxiaoke.common.model;

/**
 * table name:  fxk_status_report
 * author name: chylee
 * create time: 2020-02-27 21:40:47
 */ 
public class FxkReport {

	private int id;
	private String type;
	private String no;
	private String owner;
	private String error;

	public FxkReport() {
		super();
	}
	public FxkReport(int id,String type,String no,String owner,String error) {
		this.id=id;
		this.type=type;
		this.no=no;
		this.owner=owner;
		this.error=error;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public void setNo(String no){
		this.no=no;
	}
	public String getNo(){
		return no;
	}
	public void setOwner(String owner){
		this.owner=owner;
	}
	public String getOwner(){
		return owner;
	}
	public void setError(String error){
		this.error=error;
	}
	public String getError(){
		return error;
	}
	@Override
	public String toString() {
		return "fxk_status_report[" + 
			"id=" + id + 
			", type=" + type + 
			", no=" + no + 
			", owner=" + owner + 
			", error=" + error +
			"]";
	}
}

