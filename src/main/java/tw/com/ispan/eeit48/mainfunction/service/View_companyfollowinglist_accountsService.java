package tw.com.ispan.eeit48.mainfunction.service;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.ispan.eeit48.mainfunction.model.table.Account;
import tw.com.ispan.eeit48.mainfunction.model.table.CompanyFollowingList;
import tw.com.ispan.eeit48.mainfunction.repository.AccountsRepository;
import tw.com.ispan.eeit48.mainfunction.repository.CompanyFollowingListRepository;
import tw.com.ispan.eeit48.mainfunction.repository.View_companyfollowinglist_accountsRepository;
import tw.com.ispan.eeit48.mainfunction.model.view.CompanyFollowingList_Account;
import static tw.com.ispan.eeit48.mainfunction.service.AuthService.getCurrentUserId;

@Service
public class View_companyfollowinglist_accountsService {
	@Autowired
	private View_companyfollowinglist_accountsRepository view_companyfollowinglist_accountRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private CompanyFollowingListRepository companyFollowingListReposutory;
	@Autowired
	private View_companyfollowinglist_accountsRepository view_companyfollowinglist_accountsRepository;

	/**
	 * 找到使用者的關聯廠商
	 * @return
	 */
	public String selectUserFollowListForCompany() {
		int userId = getCurrentUserId();

		JSONArray ListAll = new JSONArray();
		List<CompanyFollowingList_Account> beans = view_companyfollowinglist_accountRepository
				.findAllByBuyerid(userId); //

		if (beans != null) {
			JSONArray ListofFriend = new JSONArray();
			for (CompanyFollowingList_Account bean : beans) {
				if (bean != null) {
					ListofFriend.put(bean.toJsonObject()); // 把所有關聯場商找出來
				}
			}
			int length = ListofFriend.length();
			int[] sellerid = new int[length]; // 把各欄位的值單獨放在各自陣列
			String[] companyname = new String[length];
			String[] companyphone = new String[length];
			String[] taxid = new String[length];
			String[] address = new String[length];
			String[] email = new String[length];
			String[] contactperson = new String[length];
			String[] contactpersonnum = new String[length];
			String[] fax = new String[length];
			String[] bankaccount = new String[length];
			String[] bankname = new String[length];
			String[] lineaccount = new String[length];
			String[] bankswiftcode = new String[length];

			for (int i = 0; i < ListofFriend.length(); i++) {
				sellerid[i] = (int) ListofFriend.getJSONObject(i).get("sellerid");
				companyname[i] = (String) ListofFriend.getJSONObject(i).get("companyname");
				companyphone[i] = (String) ListofFriend.getJSONObject(i).get("companyphone");
				taxid[i] = (String) ListofFriend.getJSONObject(i).get("taxid");
				address[i] = (String) ListofFriend.getJSONObject(i).get("address");
				email[i] = (String) ListofFriend.getJSONObject(i).get("email");
				contactperson[i] = (String) ListofFriend.getJSONObject(i).get("contactperson");
				contactpersonnum[i] = (String) ListofFriend.getJSONObject(i).get("contactpersonnum");
				fax[i] = (String) ListofFriend.getJSONObject(i).get("fax");
				bankaccount[i] = (String) ListofFriend.getJSONObject(i).get("bankaccount");
				bankname[i] = (String) ListofFriend.getJSONObject(i).get("bankname");
				lineaccount[i] = (String) ListofFriend.getJSONObject(i).get("lineaccount");
				bankswiftcode[i] = (String) ListofFriend.getJSONObject(i).get("bankswiftcode");
			}

			JSONObject[] obj;
			obj = new JSONObject[ListofFriend.length()]; // 新增obj陣列

			for (int i = 0; i < ListofFriend.length(); i++) {
				obj[i] = new JSONObject();
			}
			for (int i = 0; i < ListofFriend.length(); i++) { // 把各欄位的值組合成一個JSONArray
				obj[i].put("supplierid", sellerid[i]);
				obj[i].put("suppliercompanyname", companyname[i]);
				obj[i].put("companyphone", companyphone[i]);
				obj[i].put("taxid", taxid[i]);
				obj[i].put("address", address[i]);
				obj[i].put("email", email[i]);
				obj[i].put("contactperson", contactperson[i]);
				obj[i].put("contactpersonnum", contactpersonnum[i]);
				obj[i].put("fax", fax[i]);
				obj[i].put("bankaccount", bankaccount[i]);
				obj[i].put("bankname", bankname[i]);
				obj[i].put("lineaccount", lineaccount[i]);
				obj[i].put("bankswiftcode", bankswiftcode[i]);
				ListAll.put(obj[i]);
			}
		}
		return ListAll.toString();
	}

	public JSONArray userFollowNewCompany(String companyName) {
		int userId = getCurrentUserId();

		JSONArray ListofNew = new JSONArray();
		boolean stepOne = false;
		int switchopen = 0;
		int companyid;
		List<Account> beana = accountsRepository.findAllByCompanyname(companyName);

		if (beana != null) {
			JSONArray ListofAccount = new JSONArray();
			for (Account bean : beana) {
				if (bean != null) {
					ListofAccount.put(bean.toJsonObject()); // 把所有關聯場商找出來
				}
			}
			companyid = (int) ListofAccount.getJSONObject(0).get("accountid");

			if (switchopen == 0) {
				CompanyFollowingList in = new CompanyFollowingList();
				in.setBuyerId(userId);
				in.setSellerId(companyid);
				companyFollowingListReposutory.save(in);
				stepOne = true;
			}

			if (stepOne == true) {
				List<CompanyFollowingList_Account> beans = view_companyfollowinglist_accountRepository
						.findAllByCompanyname(companyName);

				if (beans != null) {
					JSONArray lista = new JSONArray();
					for (CompanyFollowingList_Account bean : beans) {
						if (bean != null) {
							lista.put(bean.toJsonObject()); // 把所有關聯場商找出來
						}
					}
					int[] sellerida = new int[10];
					String[] companynamelist = new String[10];
					String[] companyphone = new String[10];
					String[] taxid = new String[10];
					String[] address = new String[10];
					String[] email = new String[10];
					String[] contactperson = new String[10];
					String[] contactpersonnum = new String[10];
					String[] fax = new String[10];
					String[] bankaccount = new String[10];
					String[] bankname = new String[10];
					String[] lineaccount = new String[10];
					int[] bankswiftcode = new int[10];

					for (int i = 0; i < lista.length(); i++) {
						sellerida[i] = (int) lista.getJSONObject(i).get("sellerid");
						companynamelist[i] = (String) lista.getJSONObject(i).get("companyname");
						companyphone[i] = (String) lista.getJSONObject(i).get("companyphone");
						taxid[i] = (String) lista.getJSONObject(i).get("taxid");
						address[i] = (String) lista.getJSONObject(i).get("address");
						email[i] = (String) lista.getJSONObject(i).get("email");
						contactperson[i] = (String) lista.getJSONObject(i).get("contactperson");
						contactpersonnum[i] = (String) lista.getJSONObject(i).get("contactpersonnum");
						fax[i] = (String) lista.getJSONObject(i).get("fax");
						bankaccount[i] = (String) lista.getJSONObject(i).get("bankaccount");
						bankname[i] = (String) lista.getJSONObject(i).get("bankname");
						lineaccount[i] = (String) lista.getJSONObject(i).get("lineaccount");
						bankswiftcode[i] = (int) lista.getJSONObject(i).get("bankswiftcode");
					}
					JSONObject[] obj;
					obj = new JSONObject[lista.length()];

					for (int i = 0; i < lista.length(); i++) {
						obj[i] = new JSONObject();
					}
					for (int i = 0; i < lista.length(); i++) {
						obj[i].put("sellerid", sellerida[i]);
						obj[i].put("companyname", companynamelist[i]);
						obj[i].put("companyphone", companyphone[i]);
						obj[i].put("taxid", taxid[i]);
						obj[i].put("address", address[i]);
						obj[i].put("email", email[i]);
						obj[i].put("contactperson", contactperson[i]);
						obj[i].put("contactpersonnum", contactpersonnum[i]);
						obj[i].put("fax", fax[i]);
						obj[i].put("bankaccount", bankaccount[i]);
						obj[i].put("bankname", bankname[i]);
						obj[i].put("lineaccount", lineaccount[i]);
						obj[i].put("bankswiftcode", bankswiftcode[i]);
						ListofNew.put(obj[i]);
					}
				}
			}
		}
		return null;
	}
}
