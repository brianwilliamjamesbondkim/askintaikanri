package com.ascom.kintai.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ascom.kintai.dao.KintaiUserDao;
import com.ascom.kintai.util.KintaiConstant;
import com.ascom.kintai.vo.AppSet;
import com.ascom.kintai.vo.WorkappInfo;
import com.ascom.kintai.vo.WorkappUser;

@Repository
public class KintaiUserService {

	@Autowired
	KintaiUserDao wdao;

	public ArrayList<Object> workInfo(String email, String date) {
		ArrayList<Object> AllWorkData = wdao.workInfo(email, date);

		return AllWorkData;
	}

	public int UpdateWorkInfo(WorkappInfo updateInfo) {

		int result = wdao.UpdateWorkInfo(updateInfo);

		return result;
	}

	public void updateUserAppWorkLocation(AppSet set, String email) {

		wdao.updateUserAppWorkLocation(set, email);
	}

	public void updateUserAppLanguage(AppSet set, String email) {

		wdao.updateUserAppLanguage(set, email);
	}

	public void updateUserPassword(String pwd, String email) {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPwd = passwordEncoder.encode(pwd);

		wdao.updateUserPassword(hashedPwd, email);
	}

	public String getCurrentTime() {
		String currentTime = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentTime = sdf.format(cal.getTime());
		System.out.println(currentTime);
		return currentTime;
	}

	public String getComment() {
		String comment = "";
		comment = wdao.getComment();
		System.out.println(comment);
		return comment;
	}

	public int vacationInsert(String email) {
		int resultVacation = 0;
		resultVacation = wdao.vacationInsert(email);
		return resultVacation;
	}

	public int taikinInsert(String workDate, String endTime, String restTime, String email,String workState) {
		int resultTaikin = 0;

		String new_endTime = workDate + " " + endTime;
		System.out.println(new_endTime);

		WorkappInfo workappinfo = new WorkappInfo(email, "", "", new_endTime, restTime, "", workState);
		
		resultTaikin = wdao.taikinInsert(workappinfo);
		
		if (resultTaikin == 1) {
			wdao.worktimeInsert(email);
		}
		return resultTaikin;
	}

	public int shukinInsert(String workDate, String startTime, String email, String workState) {
		int resultShukin = 0;
		
		String new_startTime = workDate + " " + startTime;
		WorkappInfo workappinfo = new WorkappInfo(email, workDate, new_startTime, "", "", "", workState);
		
		System.out.println(email + "/" + workDate + "/" + startTime);
		resultShukin = wdao.shukinInsert(workappinfo);
		
		return resultShukin;
	}
}
