package com.example.foodbookbd;

public class ReviewItem {
	
	long reviewId,restId;
	float rank;
	String reviewerName,reviewDetails;
	
	
	public ReviewItem(long reviewId, long restId, long rank,
			String reviewerName, String reviewDetails) {
		super();
		this.reviewId = reviewId;
		this.restId = restId;
		this.rank = rank;
		this.reviewerName = reviewerName;
		this.reviewDetails = reviewDetails;
	}
	
	public ReviewItem() {
		
	}
	
	public long getReviewId() {
		return reviewId;
	}

	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}

	public long getRestId() {
		return restId;
	}

	public void setRestId(long restId) {
		this.restId = restId;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getReviewDetails() {
		return reviewDetails;
	}

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetails = reviewDetails;
	}

	@Override
	public String toString() {
		return "ReviewItem [reviewId=" + reviewId + ", restId=" + restId
				+ ", rank=" + rank + ", reviewerName=" + reviewerName
				+ ", reviewDetails=" + reviewDetails + "]";
	}

	public float getRank() {
		return rank;
	}

	public void setRank(float rate) {
		this.rank = rate;
	}
	
	

}
