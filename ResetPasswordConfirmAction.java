package com.internousdev.jaguar.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.jaguar.dao.UserInfoDAO;
import com.internousdev.jaguar.util.CommonUtility;
import com.internousdev.jaguar.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordConfirmAction extends ActionSupport implements SessionAware{
	private String userId;
	private String password;
	private String newPassword;
	private String reConfirmationPassword;
	private List<String> userIdErrorMessageList;
	private List<String> passwordErrorMessageList;
	private List<String> newPasswordErrorMessageList;
	private List<String> reConfirmationNewPasswordErrorMessageList;
	private String passwordIncorrectErrorMessage;
	private String newPasswordIncorrectErrorMessage;
	private String concealedPassword;
	private Map<String, Object> session;

	public String execute(){

		String result=ERROR;

		InputChecker ic=new InputChecker();
		//sessionにuserIdを格納
		session.put("userIdForResetPassword", userId);

		userIdErrorMessageList=ic.doCheck("ユーザーID",userId, 1, 8, true, false, false, true, false, false);
		passwordErrorMessageList=ic.doCheck("現在のパスワード", password, 1, 16, true, false, false, true, false, false);
		newPasswordErrorMessageList=ic.doCheck("新しいパスワード", newPassword, 1, 16, true, false, false, true, false, false);
		reConfirmationNewPasswordErrorMessageList=ic.doCheck("新しいパスワード(再確認)", reConfirmationPassword, 1, 16, true, false, false, true, false, false);
		//それぞれ入力された値が正規表現にマッチするか

		if(userIdErrorMessageList.size()>0
		||passwordErrorMessageList.size()>0
		||newPasswordErrorMessageList.size()>0
		||reConfirmationNewPasswordErrorMessageList.size()>0){
			return result;
		}
		//Listの中身が0より大きいときresult(ERROR)を返す

		UserInfoDAO userInfoDAO=new UserInfoDAO();
		if(!userInfoDAO.isExistsUserInfo(userId, password)){
			passwordIncorrectErrorMessage="ユーザーIDまたは現在のパスワードが異なります。";
			return result;
		}
		//ユーザーの存在確認、存在しないときにif文を実行
		//戻り値としてresult(ERROR)を返す

		newPasswordIncorrectErrorMessage=ic.doPasswordCheck(newPassword, reConfirmationPassword);
		//一度目のパスワードと二度目のパスワードが同じか検証

		if(newPasswordIncorrectErrorMessage==null){
			CommonUtility commonUtility=new CommonUtility();
			concealedPassword=commonUtility.concealPassword(newPassword);
			session.put("newPassword", newPassword);
			result=SUCCESS;
			session.put("checkFlg","1");
		}
		//入力した新しいパスワードが間違ってなければ隠す、sessionに格納
		return result;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setNewPassword(String newPassword){
		this.newPassword=newPassword;
	}

	public String getReConfirmationPassword(){
		return reConfirmationPassword;
	}

	public void setReConfirmationPassword(String reConfirmationPassword){
		this.reConfirmationPassword=reConfirmationPassword;
	}

	public List<String> getUserIdErrorMessageList(){
		return userIdErrorMessageList;
	}

	public void setUserIdErrorMessageList(List<String> userIdErrorMessageList){
		this.userIdErrorMessageList=userIdErrorMessageList;
	}

	public List<String> getPasswordErrorMessageList(){
		return passwordErrorMessageList;
	}

	public void setPasswordErrorMessageList(List<String> passwordErrorMessageList){
		this.passwordErrorMessageList=passwordErrorMessageList;
	}

	public List<String> getNewPasswordErrorMessageList(){
		return newPasswordErrorMessageList;
	}

	public void setNewPasswordErrorMessageList(List<String> newPasswordErrorMessageList){
		this.newPasswordErrorMessageList=newPasswordErrorMessageList;
	}

	public List<String> getReConfirmationNewPasswordErrorMessageList(){
		return reConfirmationNewPasswordErrorMessageList;
	}

	public void setReConfirmationNewPasswordErrorMessageList(List<String> reConfirmationNewPasswordErrorMessageList){
		this.reConfirmationNewPasswordErrorMessageList=reConfirmationNewPasswordErrorMessageList;
	}

	public String getPasswordIncorrectErrorMessage(){
		return passwordIncorrectErrorMessage;
	}

	public void setPasswordIncorrectErrorMessage(String passwordIncorrectErrorMessage){
		this.passwordIncorrectErrorMessage=passwordIncorrectErrorMessage;
	}

	public String getNewPasswordIncorrectErrorMessage(){
		return newPasswordIncorrectErrorMessage;
	}

	public void setNewPasswordIncorrectErrorMessage(String newPasswordIncorrectErrorMessage){
		this.newPasswordIncorrectErrorMessage=newPasswordIncorrectErrorMessage;
	}

	public String getConcealedPassword(){
		return concealedPassword;
	}

	public void setConcealedPassword(String concealedPassword){
		this.concealedPassword=concealedPassword;
	}

	public Map<String, Object> getSession(){
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
