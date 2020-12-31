package com.cmsz.paas.common.model.k8s.v1;

public class HorizontalPodAutoscaler {
   private String kind;
  
   private String apiversion;
  
   private ObjectMeta metadata;
  
   private HorizontalPodAutoscalerSpec spec;
  
   private HorizontalPodAutoscalerStatus status;
	  
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getApiversion() {
		return apiversion;
	}

	public void setApiversion(String apiversion) {
		this.apiversion = apiversion;
	}

	public ObjectMeta getMetadata() {
		return metadata;
	}

	public void setMetadata(ObjectMeta metadata) {
		this.metadata = metadata;
	}

	public HorizontalPodAutoscalerSpec getSpec() {
		return spec;
	}

	public void setSpec(HorizontalPodAutoscalerSpec spec) {
		this.spec = spec;
	}

	public HorizontalPodAutoscalerStatus getStatus() {
		return status;
	}

	public void setStatus(HorizontalPodAutoscalerStatus status) {
		this.status = status;
	}

	public String toString(){
		return  "HorizontalPodAutoscaler [kind=" + kind + ", apiversion=" + apiversion
				+ ", metadata=" + metadata +", spec=" + spec +",status=" + status + "]";
		
	}
}
