package vip.creeper.mcserverplugins.creepermarriage.marriage;

public class ProposeRequest {
	private String partner;
	private PayType payType;
	public ProposeRequest(final String partner,final PayType payType) {
		this.partner=partner;
		this.payType=payType;
	}
	public String getPartnerName() {
		return this.partner;
	}
	public PayType getPayType() {
		return this.payType;
	}
}
