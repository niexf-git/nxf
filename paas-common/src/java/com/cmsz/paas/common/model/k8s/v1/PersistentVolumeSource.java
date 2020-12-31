package com.cmsz.paas.common.model.k8s.v1;

public class PersistentVolumeSource {

	private GCEPersistentDiskVolumeSource gcePersistentDisk;
	
	private AWSElasticBlockStoreVolumeSource awsElasticBlockStore;
	
	private HostPathVolumeSource hostPath;
	
	private GlusterfsVolumeSource glusterfs;
	
	private NFSVolumeSource nfs;
	
	private RBDVolumeSource rbd;
	
	private ISCSIVolumeSource iscsi;
	
	private CinderVolumeSource cinder;
	
	private CephFSVolumeSource cephfs;
	
	private FCVolumeSource fc;
	
	private FlockerVolumeSource flocker;
	
	private FlexVolumeSource flexVolume;
	
	private AzureFileVolumeSource azureFile;
	
	private VsphereVirtualDiskVolumeSource vsphereVolume;
	
	private QuobyteVolumeSource quobyte;
	
	private AzureDiskVolumeSource azureDisk;
	
	private PhotonPersistentDiskVolumeSource photonPersistentDisk;
	
	public GCEPersistentDiskVolumeSource getGcePersistentDisk() {
		return gcePersistentDisk;
	}

	public void setGcePersistentDisk(GCEPersistentDiskVolumeSource gcePersistentDisk) {
		this.gcePersistentDisk = gcePersistentDisk;
	}

	public AWSElasticBlockStoreVolumeSource getAwsElasticBlockStore() {
		return awsElasticBlockStore;
	}

	public void setAwsElasticBlockStore(AWSElasticBlockStoreVolumeSource awsElasticBlockStore) {
		this.awsElasticBlockStore = awsElasticBlockStore;
	}

	public HostPathVolumeSource getHostPath() {
		return hostPath;
	}

	public void setHostPath(HostPathVolumeSource hostPath) {
		this.hostPath = hostPath;
	}

	public GlusterfsVolumeSource getGlusterfs() {
		return glusterfs;
	}

	public void setGlusterfs(GlusterfsVolumeSource glusterfs) {
		this.glusterfs = glusterfs;
	}

	public NFSVolumeSource getNfs() {
		return nfs;
	}

	public void setNfs(NFSVolumeSource nfs) {
		this.nfs = nfs;
	}

	public RBDVolumeSource getRbd() {
		return rbd;
	}

	public void setRbd(RBDVolumeSource rbd) {
		this.rbd = rbd;
	}

	public ISCSIVolumeSource getIscsi() {
		return iscsi;
	}

	public void setIscsi(ISCSIVolumeSource iscsi) {
		this.iscsi = iscsi;
	}

	public CinderVolumeSource getCinder() {
		return cinder;
	}

	public void setCinder(CinderVolumeSource cinder) {
		this.cinder = cinder;
	}

	public CephFSVolumeSource getCephfs() {
		return cephfs;
	}

	public void setCephfs(CephFSVolumeSource cephfs) {
		this.cephfs = cephfs;
	}

	public FCVolumeSource getFc() {
		return fc;
	}

	public void setFc(FCVolumeSource fc) {
		this.fc = fc;
	}

	public FlockerVolumeSource getFlocker() {
		return flocker;
	}

	public void setFlocker(FlockerVolumeSource flocker) {
		this.flocker = flocker;
	}

	public FlexVolumeSource getFlexVolume() {
		return flexVolume;
	}

	public void setFlexVolume(FlexVolumeSource flexVolume) {
		this.flexVolume = flexVolume;
	}

	public AzureFileVolumeSource getAzureFile() {
		return azureFile;
	}

	public void setAzureFile(AzureFileVolumeSource azureFile) {
		this.azureFile = azureFile;
	}

	public VsphereVirtualDiskVolumeSource getVsphereVolume() {
		return vsphereVolume;
	}

	public void setVsphereVolume(VsphereVirtualDiskVolumeSource vsphereVolume) {
		this.vsphereVolume = vsphereVolume;
	}

	public QuobyteVolumeSource getQuobyte() {
		return quobyte;
	}

	public void setQuobyte(QuobyteVolumeSource quobyte) {
		this.quobyte = quobyte;
	}

	public AzureDiskVolumeSource getAzureDisk() {
		return azureDisk;
	}

	public void setAzureDisk(AzureDiskVolumeSource azureDisk) {
		this.azureDisk = azureDisk;
	}

	public PhotonPersistentDiskVolumeSource getPhotonPersistentDisk() {
		return photonPersistentDisk;
	}

	public void setPhotonPersistentDisk(PhotonPersistentDiskVolumeSource photonPersistentDisk) {
		this.photonPersistentDisk = photonPersistentDisk;
	}

	@Override
	public String toString() {
		return "Volume "
				+ "[hostPath=" + hostPath
				+ ", gcePersistentDisk=" + gcePersistentDisk
				+ ", awsElasticBlockStore=" + awsElasticBlockStore 
				+ ", nfs=" + nfs 
				+ ", iscsi=" + iscsi 
				+ ", glusterfs=" + glusterfs 
				+ ", rbd=" + rbd
				+ ", flexVolume=" + flexVolume
				+ ", cinder=" + cinder
				+ ", cephfs=" + cephfs
				+ ", flocker=" + flocker
				+ ", fc=" + fc
				+ ", azureFile=" + azureFile
				+ ", vsphereVolume=" + vsphereVolume
				+ ", quobyte=" + quobyte
				+ ", azureDisk=" + azureDisk
				+ ", photonPersistentDisk=" + photonPersistentDisk + "]";
	}
	
}
