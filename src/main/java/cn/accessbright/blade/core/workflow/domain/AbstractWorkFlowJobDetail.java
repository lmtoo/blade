package cn.accessbright.blade.core.workflow.domain;

public abstract class AbstractWorkFlowJobDetail implements WorkflowJobDetail {
	private String id;
	private String jobId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}