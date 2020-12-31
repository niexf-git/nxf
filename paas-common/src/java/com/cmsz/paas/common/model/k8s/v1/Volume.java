package com.cmsz.paas.common.model.k8s.v1;

public class Volume {

	private String name;

	private HostPathVolumeSource hostPath;

	private EmptyDirVolumeSource emptyDir;

	private GCEPersistentDiskVolumeSource gcePersistentDisk;

	private AWSElasticBlockStoreVolumeSource awsElasticBlockStore;

	private GitRepoVolumeSource gitRepo;

	private SecretVolumeSource secret;

	private NFSVolumeSource nfs;

	private ISCSIVolumeSource iscsi;

	private GlusterfsVolumeSource glusterfs;

	private PersistentVolumeClaimVolumeSource persistentVolumeClaim;

	private RBDVolumeSource rbd;
	
	private FlexVolumeSource flexVolume;
	
	private CinderVolumeSource cinder;
	
	private CephFSVolumeSource cephfs;
	
	private FlockerVolumeSource flocker;
	
	private DownwardAPIVolumeSource downwardAPI;
	
	private FCVolumeSource fc;
	
	private AzureFileVolumeSource azureFile;
	
	private ConfigMapVolumeSource configMap;
	
	private VsphereVirtualDiskVolumeSource vsphereVolume;
	
	private QuobyteVolumeSource quobyte;
	
	private AzureDiskVolumeSource azureDisk;
	
	private PhotonPersistentDiskVolumeSource photonPersistentDisk;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HostPathVolumeSource getHostPath() {
		return hostPath;
	}

	public void setHostPath(HostPathVolumeSource hostPath) {
		this.hostPath = hostPath;
	}

	public EmptyDirVolumeSource getEmptyDir() {
		return emptyDir;
	}

	public void setEmptyDir(EmptyDirVolumeSource emptyDir) {
		this.emptyDir = emptyDir;
	}

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

	public GitRepoVolumeSource getGitRepo() {
		return gitRepo;
	}

	public void setGitRepo(GitRepoVolumeSource gitRepo) {
		this.gitRepo = gitRepo;
	}

	public SecretVolumeSource getSecret() {
		return secret;
	}

	public void setSecret(SecretVolumeSource secret) {
		this.secret = secret;
	}

	public NFSVolumeSource getNfs() {
		return nfs;
	}

	public void setNfs(NFSVolumeSource nfs) {
		this.nfs = nfs;
	}

	public ISCSIVolumeSource getIscsi() {
		return iscsi;
	}

	public void setIscsi(ISCSIVolumeSource iscsi) {
		this.iscsi = iscsi;
	}

	public GlusterfsVolumeSource getGlusterfs() {
		return glusterfs;
	}

	public void setGlusterfs(GlusterfsVolumeSource glusterfs) {
		this.glusterfs = glusterfs;
	}

	public PersistentVolumeClaimVolumeSource getPersistentVolumeClaim() {
		return persistentVolumeClaim;
	}

	public void setPersistentVolumeClaim(PersistentVolumeClaimVolumeSource persistentVolumeClaim) {
		this.persistentVolumeClaim = persistentVolumeClaim;
	}

	public RBDVolumeSource getRbd() {
		return rbd;
	}

	public void setRbd(RBDVolumeSource rbd) {
		this.rbd = rbd;
	}

	public FlexVolumeSource getFlexVolume() {
		return flexVolume;
	}

	public void setFlexVolume(FlexVolumeSource flexVolume) {
		this.flexVolume = flexVolume;
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

	public FlockerVolumeSource getFlocker() {
		return flocker;
	}

	public void setFlocker(FlockerVolumeSource flocker) {
		this.flocker = flocker;
	}

	public DownwardAPIVolumeSource getDownwardAPI() {
		return downwardAPI;
	}

	public void setDownwardAPI(DownwardAPIVolumeSource downwardAPI) {
		this.downwardAPI = downwardAPI;
	}

	public FCVolumeSource getFc() {
		return fc;
	}

	public void setFc(FCVolumeSource fc) {
		this.fc = fc;
	}

	public AzureFileVolumeSource getAzureFile() {
		return azureFile;
	}

	public void setAzureFile(AzureFileVolumeSource azureFile) {
		this.azureFile = azureFile;
	}

	public ConfigMapVolumeSource getConfigMap() {
		return configMap;
	}

	public void setConfigMap(ConfigMapVolumeSource configMap) {
		this.configMap = configMap;
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
				+ "[name=" + name 
				+ ", hostPath=" + hostPath
				+ ", emptyDir=" + emptyDir
				+ ", gcePersistentDisk=" + gcePersistentDisk
				+ ", awsElasticBlockStore=" + awsElasticBlockStore 
				+ ", gitRepo=" + gitRepo
				+ ", secret=" + secret 
				+ ", nfs=" + nfs 
				+ ", iscsi=" + iscsi 
				+ ", glusterfs=" + glusterfs 
				+ ", persistentVolumeClaim=" + persistentVolumeClaim 
				+ ", rbd=" + rbd
				+ ", flexVolume=" + flexVolume
				+ ", cinder=" + cinder
				+ ", cephfs=" + cephfs
				+ ", flocker=" + flocker
				+ ", downwardAPI=" + downwardAPI
				+ ", fc=" + fc
				+ ", azureFile=" + azureFile
				+ ", configMap=" + configMap
				+ ", vsphereVolume=" + vsphereVolume
				+ ", quobyte=" + quobyte
				+ ", azureDisk=" + azureDisk
				+ ", photonPersistentDisk=" + photonPersistentDisk + "]";
	}
}
