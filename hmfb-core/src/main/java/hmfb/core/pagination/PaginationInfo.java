package hmfb.core.pagination;

/**

 * @FileName : PaginationInfo.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : 페이지처리 정보 클래스

 * @변경이력 :

 */

public class PaginationInfo {

	private int currentPageNo;
	private int recordCountPerPage;
	private int pageSize;
	private int totalRecordCount;	
	private int totalPageCount;
	private int firstPageNoOnPageList;
	private int lastPageNoOnPageList;
	private int currentRecordIndex;
	private int nextRecordIndex;
	
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		getFirstPageNoOnPageList();
		getLastPageNoOnPageList();
		
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	
	public int getTotalPageCount() {
		totalPageCount = ((getTotalRecordCount() - 1) / getRecordCountPerPage()) + 1;
		return totalPageCount;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getLastPageNo() {
		return getTotalPageCount();
	}

	public int getFirstPageNoOnPageList() {
		firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
		return firstPageNoOnPageList;
	}

	public int getLastPageNoOnPageList() {
		lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
		if (lastPageNoOnPageList > getTotalPageCount()) {
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}

	public int getCurrentRecordIndex() {
		currentRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
		return currentRecordIndex;
	}

	public int getNextRecordIndex() {
		nextRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
		return nextRecordIndex;
	}
	
}
