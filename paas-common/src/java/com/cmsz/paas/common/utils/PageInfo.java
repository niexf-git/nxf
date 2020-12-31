package com.cmsz.paas.common.utils;

public class PageInfo {
		//当前页
		private int page;
		
		//每页多少条
		private int rows;
		
		//开始条数
		private int start;
		
		
		public int getStart() {
			return (page-1)*rows;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getPage() {
			return page-1;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getRows() {
			return rows;
			
		}

		public void setRows(int rows) {
			this.rows = rows;
		}
}
