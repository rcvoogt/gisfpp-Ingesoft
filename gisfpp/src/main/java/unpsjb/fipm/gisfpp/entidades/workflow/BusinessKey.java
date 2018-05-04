package unpsjb.fipm.gisfpp.entidades.workflow;

public enum BusinessKey {
	
	solicitudNuevaIsfpp("solicitudNuevaIsfpp:1:10012"),
	prcConvocatoriaYConvenios("	prcConvocatoriaYConvenios"),
	prcGeneracionInformesFinales("prcGeneracionInformesFinales"),
	procIsfppSuspendida("procIsfppSuspendida");
	
	private String keyBusiness;
	
	private BusinessKey(String keyBusiness) {
		this.keyBusiness = keyBusiness;
	}

	public String getKeyBusiness() {
		return keyBusiness;
	}
	


}
