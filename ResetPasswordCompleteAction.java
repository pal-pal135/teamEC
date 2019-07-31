package com.internousdev.jaguar.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jaguar.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordCompleteAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;


	public String execute(){
		if(session.get("checkFlg").toString()!="1"){
			return ERROR;
		}
		//resultにERRORを代入
		String result =ERROR;
		//UserInfoDAOをuserInfoDAOにインスタンス化
		UserInfoDAO userInfoDAO=new UserInfoDAO();
		//countにuserInfoDAOのresetPasswordメソッド
		int count=userInfoDAO.resetPassword(session.get("userIdForResetPassword").toString(), session.get("newPassword").toString());
		//countの件数が0より大きいときif文を実行
		if(count>0){

			//resultにSUCCESSを代入
			result=SUCCESS;
		}
		//それぞれsessionから削除
		session.remove("userIdForResetPasswod");
		session.remove("newPassword");
		session.remove("checkFlg");
		//戻り値としてresult(SUCCESS)を返す
		return result;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
