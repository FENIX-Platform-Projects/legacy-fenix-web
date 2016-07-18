package org.fao.fenix.web.modules.amis.client.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fenix.web.modules.amis.client.control.menu.AMISMenuController;
import org.fao.fenix.web.modules.amis.client.view.home.AMISHome;
import org.fao.fenix.web.modules.amis.client.view.input.AMISInput;
import org.fao.fenix.web.modules.amis.client.view.login.AMISLoginPanel;
import org.fao.fenix.web.modules.amis.client.view.login.AMISLoginRegisterPanel;
import org.fao.fenix.web.modules.amis.client.view.login.AMISRegistrationForm;
import org.fao.fenix.web.modules.amis.client.view.menu.AMISMainMenu;
import org.fao.fenix.web.modules.amis.common.constants.AMISConstants;
import org.fao.fenix.web.modules.amis.common.constants.AMISCurrentView;
import org.fao.fenix.web.modules.amis.common.model.AMISCodesModelData;
import org.fao.fenix.web.modules.amis.common.services.AMISServiceEntry;
import org.fao.fenix.web.modules.amis.common.vo.AMISQueryVO;
import org.fao.fenix.web.modules.amis.common.vo.AMISResultVO;
import org.fao.fenix.web.modules.amis.common.vo.CCBS;
import org.fao.fenix.web.modules.core.client.security.FenixUser;
import org.fao.fenix.web.modules.core.client.utils.ClickHtml;
import org.fao.fenix.web.modules.core.common.exception.FenixGWTException;
import org.fao.fenix.web.modules.core.common.services.UserServiceEntry;
import org.fao.fenix.web.modules.core.common.vo.LoginResponseVo;
import org.fao.fenix.web.modules.lang.client.BabelFish;
import org.fao.fenix.web.modules.shared.window.client.view.FenixAlert;
import org.fao.fenix.web.modules.shared.window.client.view.utils.LoadingWindow;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.fao.fenix.web.modules.amis.client.control.history.AMISHistoryController;





public class AMISLogInController extends AMISController {
	
	public static SelectionListener<ButtonEvent> openRegisterPanelForm(final AMISRegistrationForm amisRegistrationForm) {
		return new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				System.out.println("Class: AMISController Function: openRegisterPanelForm Text: Before call build ");
				amisRegistrationForm.build();
				System.out.println("Class: AMISController Function: openRegisterPanelForm Text: After build ");
			}
			};
	}
 	
 	public static SelectionListener<ButtonEvent> cancelPanelRegistrationForm(final AMISRegistrationForm amisRegistrationForm) {
		return new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				amisRegistrationForm.getWindow().hide();
			}
			};
	}
 	
 	public static SelectionListener<ButtonEvent> requestAccessPanelRegistrationForm(final AMISRegistrationForm amisRegistrationForm) {
		return new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				//AMIS-Secretariat@fao.org
				String errorText = parametersCheck(amisRegistrationForm);
				if(!errorText.equals(""))
				{
					System.out.println("Error come back in if text : "+errorText);
					//Error 
					FenixAlert.error("Error", errorText);
				}
				else if(!emailParameterCheck(amisRegistrationForm))
				{
					//Error 
					FenixAlert.error("Error", "E-mail address is not valid!");
				}
				else
				{
					Map<String, String> m = new HashMap<String, String>();
					m.put("Name", amisRegistrationForm.getNameTextField().getValue());
					m.put("Surname", amisRegistrationForm.getSurnameTextField().getValue());
					m.put("Country", amisRegistrationForm.getCountryTextField().getValue());
					m.put("Institution", amisRegistrationForm.getInstitutionTextField().getValue());
					m.put("E-Mail", amisRegistrationForm.getEmailTextField().getValue());
					final LoadingWindow loading = new LoadingWindow("Loading", "The system is sending your e-mail to the AMIS Secretariat.", "Please wait...");
					try {
						AMISServiceEntry.getInstance().sendRegistrationEMail(m, new AsyncCallback<String>() {
							public void onSuccess(String arg0) {
								String text = "Thank you for requesting access AMIS Statistics. Your request will be processed as soon as possible and, if approved, you will receive an email confirming the activation of the account with username and password.<br><br>Best regards,<br>The AMIS Secretariat\n";
							//	FenixAlert.info("Success", arg0);
								loading.destroyLoadingBox();
								FenixAlert.infoAmisRegistration(arg0, text);
								amisRegistrationForm.getWindow().hide();
								loading.destroyLoadingBox();
								
							}
							public void onFailure(Throwable arg0) {
								loading.destroyLoadingBox();
								FenixAlert.error("Error", arg0.getMessage());
								loading.destroyLoadingBox();
							}
						});
					} catch (FenixGWTException e) {
						loading.destroyLoadingBox();
						FenixAlert.error("Error", e.getMessage());
						loading.destroyLoadingBox();
					}
				}			
			}
			};
	}
 	 
 	public static String parametersCheck(AMISRegistrationForm amisRegistrationForm)
 	{
 		System.out.println("Name 1 "+ amisRegistrationForm.getNameTextField());
 		System.out.println("Surname 1 "+ amisRegistrationForm.getSurnameTextField());
 		System.out.println("Country 1 "+amisRegistrationForm.getCountryTextField());
 		System.out.println("Institution 1 "+ amisRegistrationForm.getInstitutionTextField());
 		System.out.println("E-Mail 1 "+ amisRegistrationForm.getEmailTextField());
 		System.out.println("Name "+ amisRegistrationForm.getNameTextField().getValue());
 		System.out.println("Surname "+ amisRegistrationForm.getSurnameTextField().getValue());
 		System.out.println("Country "+amisRegistrationForm.getCountryTextField().getValue());
 		System.out.println("Institution "+ amisRegistrationForm.getInstitutionTextField().getValue());
 		System.out.println("E-Mail "+ amisRegistrationForm.getEmailTextField().getValue());
 		String errorText = "";
 		int count=0;
		if((amisRegistrationForm.getNameTextField()==null)||(amisRegistrationForm.getNameTextField().getValue()==null)||(amisRegistrationForm.getNameTextField().getValue().equals("")))
		{
			errorText = "Name, ";
			count++;
		}
		if((amisRegistrationForm.getSurnameTextField()==null)||(amisRegistrationForm.getSurnameTextField().getValue()==null)||(amisRegistrationForm.getSurnameTextField().getValue().equals("")))
		{
			errorText = errorText+"Surname, ";
			count++;
		}
		if((amisRegistrationForm.getCountryTextField()==null)||(amisRegistrationForm.getCountryTextField().getValue()==null)||(amisRegistrationForm.getCountryTextField().getValue().equals("")))
		{
			errorText = errorText+"Country, ";
			count++;
		}
		if((amisRegistrationForm.getInstitutionTextField()==null)||(amisRegistrationForm.getInstitutionTextField().getValue()==null)||(amisRegistrationForm.getInstitutionTextField().getValue().equals("")))
		{
			errorText = errorText+"Institution, ";
			count++;
		}
		if((amisRegistrationForm.getEmailTextField()==null)||(amisRegistrationForm.getEmailTextField().getValue()==null)||(amisRegistrationForm.getEmailTextField().getValue().equals("")))
		{
			errorText = errorText+"E-Mail, ";
			count++;
		}
		if(count!=0)
		{
			//Remove the last comma
			errorText= errorText.substring(0, errorText.length()-2);
			if(count>1)
			{
				errorText = "Please insert the fields: " + errorText+".";
			}
			else
			{
				errorText = "Please insert the field: " + errorText+".";
			}
		}		
		System.out.println("Error text : "+errorText);
 		return errorText;
 	}
 	
 	public static boolean emailParameterCheck(AMISRegistrationForm amisRegistrationForm)
 	{
 		String email = amisRegistrationForm.getEmailTextField().getValue();
 		boolean ris = false;
 		if (email.matches(".+@.+\\.[a-z]+"))
 		{
 			ris = true;
 		}
 		return ris;
 	}
 	
 	
	/*ORIGINAL LOG IN FUNCTION
	 * 
	 * public static Listener<ComponentEvent> loginListenerClick(final TextField<String> username, final TextField<String> password, final ComboBox<AMISCodesModelData> dataSourceListBox, final AMISInput amisInput) {
	return new Listener<ComponentEvent>() {

			public void handleEvent(ComponentEvent ce) {
				System.out.println("Class: AMISController Function: loginListenerClick Text: username= "+username.getValue() + " password "+password.getValue());
				final String usernameValue = username.getValue();
				AMISLoginPanel.getAmisUserParameters().setUsername(username.getValue());
				AMISLoginPanel.getAmisUserParameters().setPwd(password.getValue());
				UserServiceEntry.getInstance().login(username.getValue(), password.getValue(), new AsyncCallback<LoginResponseVo>() {
					public void onSuccess(LoginResponseVo vo) {
						if (vo.isSucceeded()) {
							String firstAndLastName = vo.getFenixUserVo().getFirstName() + " " + vo.getFenixUserVo().getLastName();
							String welcomeMessage = "  " + BabelFish.print().welcome() + " <b>" + firstAndLastName + "</b>";
							FenixUser.populateRoles(vo.getFenixUserVo());
							amisInput.getPanel().removeAll();
							//amisInput.getPanel().add(FormattingUtils.addVSpace(20));
							amisInput.setUsername(usernameValue);
							amisInput.getPanel().add(amisInput.buildInputButtons());
							//amisInput.getPanel().add(amisInput.buildLoginMessage(welcomeMessage));
							if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
								RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));
							
							RootPanel.get("USER_LOGIN_WELCOME").add(amisInput.buildLoginMessage(welcomeMessage));
						//	amisInput.getPanel().add(amisInput.buildInputButtons());
							//Create two combo in amisInput: country and data source
							//Set liststore....							
							
						//	amisInput.getPanel().add(amisInput.buildCountryListBox());

						//	amisInput.getPanel().getLayout().layout();

							//Find Groups the user belongs to
							//AMISServiceImpl findGroupsOfUser should be updated so that the user groups are translated to the appropriate
							//country code and label to fill the country combo 
							AMISServiceEntry.getInstance().findGroupsOfUser(username.getValue(), new AsyncCallback<List<AMISCodesModelData>>() {
								public void onSuccess(List<AMISCodesModelData> userGroups) {
									CCBS.COUNTRY_NAMES.clear();
									CCBS.COUNTRY_CODES.clear();
									if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
									{
										amisInput.getCountryListBox().getStore().removeAll();
									}
									final int iData=0;
	//								amisInput.getCountryListBox().getStore().add(new AMISCodesModelData("", ""));
									for(final AMISCodesModelData userGroupModel: userGroups)
									{
										if(!userGroupModel.getLabel().equalsIgnoreCase("ALL_USER_GROUP"))
										{
											System.out.println("Class: AMISController findGroupsOfUser : userGroup Label = "+userGroupModel.getLabel());
											System.out.println("Class: AMISController findGroupsOfUser : userGroup Code = "+userGroupModel.getCode());
											
//											amisInput.getCountryListBox().getStore().add(userGroupModel);
//											amisInput.getPanel().getLayout().layout();
											
											AMISQueryVO qvo = new AMISQueryVO();
											qvo.setOutput(AMISConstants.CODING_SYSTEM.toString());
											qvo.setCountryName(userGroupModel.getLabel());
											qvo.setTypeOfOutput(AMISConstants.AMIS_COUNTRY_CROP_COUNT.toString());
											
											//1. amisInput.getCountryCombo.getStore.clear();(or removeaLL)
											//2. amisInput.getCountryCombo.getStore.add(userGroupModel)
											//3. amisInput.getPanel().getLayout().layout(); and remove from line 515

											try {
												AMISServiceEntry.getInstance().askAMIS(qvo, new AsyncCallback<AMISResultVO>() {
													
													@SuppressWarnings("unchecked")
													public void onSuccess(AMISResultVO vo) {
														
//															CCBS.COUNTRY_NAMES.clear();
//															CCBS.COUNTRY_CODES.clear();
														
														int J=0;
														for(AMISCodesModelData topcode : vo.getCodes()) {
														//	System.out.println("Class; CCBSParameterPanel  Function: loadGaulCountries Text: topcode.getLabel() "+topcode.getLabel());
															
															CCBS.COUNTRY_NAMES.add(topcode.getLabel());
															CCBS.COUNTRY_CODES.add(topcode.getCode());	
															if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
															{
																amisInput.getCountryListBox().getStore().add(new AMISCodesModelData(topcode.getCode(), topcode.getLabel()));
															}
															//iData++;
															//store.add(topcode);
															J++;
														}
														
														System.out.println("Class: AMISController findGroupsOfUser : username = "+username);
													//	amisInput.getCountryListBox().getStore().removeAll();
													if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
													{
														System.out.println("Class: AMISController findGroupsOfUser : if((usernameValue!= null)&&(username.equals(CCBS.AMIS_SECRETARIAT)))");
													
//														for (int iCountry = 0; iCountry < CCBS.COUNTRY_NAMES.size(); iCountry++)
//														{
//															//countryListBox.addItem(CCBS.COUNTRY_NAMES.get(iCountry));
//															amisInput.getCountryListBox().getStore().add(new AMISCodesModelData(CCBS.COUNTRY_CODES.get(iCountry), CCBS.COUNTRY_NAMES.get(iCountry)));
//														}
//														for(int i=0; i< CCBS.COUNTRY_NAMES.size();i++)
//														{
//															System.out.println("********CCBS.COUNTRY_NAMES "+CCBS.COUNTRY_NAMES.get(i));
//														}
//														for(int i=0; i< CCBS.COUNTRY_CODES.size();i++)
//														{
//															System.out.println("********CCBS.COUNTRY_CODES "+CCBS.COUNTRY_CODES.get(i));
//														}
														//iData++;
														amisInput.setSelectCountryAndDatasourceInputPage();
													}
													else
													{
														final AmisUtilsController auc = new AmisUtilsController();
														System.out.println("Class: AMISController findGroupsOfUser : else((usernameValue!= null)&&(username.equals(CCBS.AMIS_SECRETARIAT)))");
														amisInput.getCountryLabel().setHtml("<div class='input_panel_text'><b>"+CCBS.COUNTRY_NAMES.get(0) +"</b></div>");
														for(int i=0; i< CCBS.COUNTRY_NAMES.size();i++)
														{
															System.out.println("********CCBS.COUNTRY_NAMES "+CCBS.COUNTRY_NAMES.get(i));
														}
														for(int i=0; i< CCBS.COUNTRY_CODES.size();i++)
														{
															System.out.println("********CCBS.COUNTRY_CODES "+CCBS.COUNTRY_CODES.get(i));
														}
														//Variable used in the Cbs Tool to know which country is used
														final String country =  CCBS.COUNTRY_NAMES.get(0);
														//Variable used in the Cbs Tool to know which country is used
														amisInput.getCountrySelected().setLabel(country);
														int i=0;
														for(i=0; i<CCBS.COUNTRY_NAMES.size(); i++)
														{
															if(CCBS.COUNTRY_NAMES.get(i).equals(country))
															{
																break;
															}
														}
														amisInput.getCountrySelected().setCode(CCBS.COUNTRY_CODES.get(i));
														amisInput.setSelectCountryTextInputPage(country);
														//Fill the data source listbox
														
														CCBS.DATA_SOURCE_NAMES.clear();
														//CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining + " (Training Set)"));
														dataSourceListBox.getStore().removeAll();
														//dataSourceListBox.getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));
													//4. The following (getAllCustomDatasets and getGroupPermission4Resource) should be in a new function and referenced when a country (i.e. a user group) has been clicked on from the getCountryCombo (user group),
													//Find All Custom Datasets
													AMISServiceEntry.getInstance().getAllCustomDatasets(new AsyncCallback<Map<Long, AMISCodesModelData>>() {
														public void onSuccess(Map<Long, AMISCodesModelData> customDatasets) {
															for(final Map.Entry<Long, AMISCodesModelData> customDatasetEntry: customDatasets.entrySet())
															{
																boolean ris = auc.isDatasourceForCountry();
																if(!ris)
																{
																	final long resourceId = customDatasetEntry.getKey();
																	final AMISCodesModelData dataSourceModel = customDatasetEntry.getValue();
																	final String dataSourceCode = dataSourceModel.getCode();

																	System.out.println("Class:AmisController Function: getAllCustomDatasets********resourceId "+resourceId);
																	System.out.println("Class:AmisController Function: getAllCustomDatasets********dataSourceModel.getCode() "+dataSourceModel.getCode()+ " dataSourceModel.getLabel()"+ dataSourceModel.getLabel());
																	System.out.println("Class:AmisController Function: getAllCustomDatasets********country "+country);
																	//Check the group permissions for each custom dataset resource			
																	if((dataSourceCode!=null)&&(dataSourceCode.contains(country)))
																	{
																	//Check the group permissions for each custom dataset resource			
																	UserServiceEntry.getInstance().getGroupPermission4Resource(country, resourceId, new AsyncCallback<Map<String, Boolean>>() {
																		public void onSuccess(Map<String, Boolean> permissionMap) {

																			//Check if all the 4 permission types (DELETE, READ, WRITE, DOWNLOAD) are set to true, if so then this dataset belongs to the group
																			if(permissionMap.get("DELETE") && permissionMap.get("READ") && permissionMap.get("WRITE") && permissionMap.get("DOWNLOAD")) {
																				System.out.println("Group: "+country+" : permission values for dataset code '"+dataSourceCode+"' = [DELETE:"+ permissionMap.get("DELETE") +" | READ:" + permissionMap.get("READ") +" | WRITE:" + permissionMap.get("WRITE") +" | DOWNLOAD:" + permissionMap.get("DOWNLOAD")+ "]");
																				//The AMISCodesModelData (dataset code and dataset title) i.e. dataSourceModel would then be added to the DATA SOURCE Combo
																				//e.g. amisInput.getDataSourceCombo.getStore.add(model)
																				//amisInput.getPanel().getLayout().layout();
																				CCBS.DATA_SOURCE_NAMES.add(new String(dataSourceModel.getLabel()));
																				CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining));
																				dataSourceListBox.getStore().add(dataSourceModel);
																				auc.setDatasourceForCountry(true);
																			}
																		}

																		public void onFailure(Throwable caught) {
																			Info.display(BabelFish.print().generalServerProblem(), caught
																					.getLocalizedMessage());
																		}
																	});
																	}
																}																
															}
															dataSourceListBox.getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));
															amisInput.getPanel().getLayout().layout();
														}

														public void onFailure(Throwable caught) {
															Info.display(BabelFish.print().generalServerProblem(), caught.getLocalizedMessage());
														}
													});
													}														
														
													//	Html selectFromComboBox = new Html("<div class='input_panel_text'><b>"+CCBS.COUNTRY_NAMES.get(0) +"</b></div>");
//														for(int i=0; i< amisInput.getCountryListBox().getStore().getCount();i++)
//														{
//															System.out.println("********Country label "+amisInput.getCountryListBox().getStore().getAt(i).getLabel());
//															System.out.println("********Country code "+amisInput.getCountryListBox().getStore().getAt(i).getCode());
//														}
														
																										}
																									
													public void onFailure(Throwable arg0) {
										
													}
												});
												
											} catch (Exception e) {
												e.printStackTrace();
											}

										}
									}
									amisInput.getPanel().getLayout().layout();
								}

								public void onFailure(Throwable e) {
									FenixAlert.error(BabelFish.print().error(), e.getMessage());
								}
							});
						} else {
							String message = vo.getResponseMessage();
							if (vo.getResponseMessage().equals("UsernameNotFoundException"))
								message = BabelFish.print().UsernameNotFoundException();
							if (vo.getResponseMessage().equals("BadCredentialsException"))
								message = BabelFish.print().BadCredentialsException();
							if (vo.getResponseMessage().equals("AuthenticationException"))
								message = BabelFish.print().AuthenticationException();
							if (vo.getResponseMessage().equals("DisabledException"))
								message =  BabelFish.print().UserGroupDisabledException();;
								
								ClickHtml html = new ClickHtml();
								html.setId("message");
								html.setHtml("<div class='login_welcome'>"+message + " <b>[click to cancel]</b></div>");
								html.setStyleAttribute("cursor", "pointer");
								html.addListener(Events.OnClick, removeLogInMessage(amisInput));
							
								
								if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
									RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));
								
								RootPanel.get("USER_LOGIN_WELCOME").add(html);
								
								//amisInput.getPanel().add(html);
								amisInput.getPanel().getLayout().layout();

						}						
					}
					public void onFailure(Throwable e) {
						FenixAlert.error(BabelFish.print().error(), e.getMessage());
					}
				});

			}
		};
	}*/
	
	
	public static Listener<ComponentEvent> loginClickListener(final TextField<String> username, final TextField<String> password, final AMISMainMenu mainMenu, final AMISLoginRegisterPanel amisLoginRegisterPanel) {
		return new Listener<ComponentEvent>() {

			public void handleEvent(ComponentEvent ce) {
				 loginAction(username, password, mainMenu, amisLoginRegisterPanel);
			}
		};
	}
	
	public static Listener<FieldEvent> loginEnterKeyListener(final TextField<String> username, final TextField<String> password, final AMISMainMenu mainMenu, final AMISLoginRegisterPanel amisLoginRegisterPanel) {
		return new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent ce) {
				
				if(ce.getKeyCode()==13) 
				 loginAction(username, password, mainMenu, amisLoginRegisterPanel);
			}
		};
	}

	
	private static void loginAction(final TextField<String> username, final TextField<String> password, final AMISMainMenu mainMenu, final AMISLoginRegisterPanel amisLoginRegisterPanel){
		System.out.println("Class: AMISLogInController Function: loginAction Text: username= "+username.getValue() + " password "+password.getValue());
		AMISLoginPanel.getAmisUserParameters().setUsername(username.getValue());
		AMISLoginPanel.getAmisUserParameters().setPwd(password.getValue());
	
		UserServiceEntry.getInstance().login(username.getValue(), password.getValue(), new AsyncCallback<LoginResponseVo>() {
			public void onSuccess(LoginResponseVo vo) {
				if (vo.isSucceeded()) {
					String firstAndLastName = vo.getFenixUserVo().getFirstName() + " " + vo.getFenixUserVo().getLastName();
					final String welcomeMessage = "  " + BabelFish.print().welcome() + " <b>" + firstAndLastName + "</b>";
					FenixUser.populateRoles(vo.getFenixUserVo());
	
					System.out.println("Class: AMISLogInController Function: loginAction Text: AMISLoginPanel.getAmisUserParameters() username= "+AMISLoginPanel.getAmisUserParameters().getUsername());
					
					//Welcome message
					if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
						RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));

					
					//Set Log Out Link
					buildLogMessage(mainMenu, logOut(mainMenu), "Log Out");
				
                    // Add INPUT DATA to MENU
                     if(username.getValue().equals(CCBS.AMIS_SECRETARIAT))  {
                        mainMenu.addInputMenuItem();
                     } else {
                        mainMenu.removeInputMenuItem();
                     }

					//SET AMIS INPUT VARIABLES
					AMISServiceEntry.getInstance().findGroupsOfUser(username.getValue(), new AsyncCallback<List<AMISCodesModelData>>() {
						public void onSuccess(final List<AMISCodesModelData> userGroups) {
							CCBS.COUNTRY_NAMES.clear();
							CCBS.COUNTRY_CODES.clear();
							
						
							//for(final AMISCodesModelData userGroupModel: userGroups)
							for(int i = 0; i < userGroups.size(); i++)	
							{
								final AMISCodesModelData userGroupModel = userGroups.get(i);

								System.out.println("Class: AMISLogInController findGroupsOfUser : userGroup code = "+userGroupModel.getCode()  + " | label = "+userGroupModel.getLabel());

								//counter to track when the userGroups loop ends
								final int counter = i+1;

								if(!userGroupModel.getLabel().equalsIgnoreCase("ALL_USER_GROUP"))
								{
									System.out.println("Class: AMISLogInController findGroupsOfUser : userGroup code = "+userGroupModel.getCode()  + " | label = "+userGroupModel.getLabel());

									AMISQueryVO qvo = new AMISQueryVO();
									qvo.setOutput(AMISConstants.CODING_SYSTEM.toString());
									qvo.setCountryName(userGroupModel.getLabel());
									qvo.setTypeOfOutput(AMISConstants.AMIS_COUNTRY_CROP_COUNT.toString());

									try {
										AMISServiceEntry.getInstance().askAMIS(qvo, new AsyncCallback<AMISResultVO>() {

											@SuppressWarnings("unchecked")
											public void onSuccess(AMISResultVO vo) {		
												for(AMISCodesModelData topcode : vo.getCodes()) {		
													CCBS.COUNTRY_NAMES.add(topcode.getLabel());
													CCBS.COUNTRY_CODES.add(topcode.getCode());	
												}

												System.out.println("Class: AMISLogInController findGroupsOfUser : username = "+username.getValue());
												System.out.println("Class: AMISLogInController Function: loginAction Text: username set= "+AMISLoginPanel.getAmisUserParameters().getUsername());

												/*for(int i= 0; i < CCBS.COUNTRY_CODES.size(); i++){
													System.out.println("Class: AMISLogInController findGroupsOfUser : CCBS.COUNTRY_CODES = "+CCBS.COUNTRY_CODES.get(i));

												}*/

												if((username.getValue()!= null)&&!(username.getValue().equals(CCBS.AMIS_SECRETARIAT)))
												{
													final AmisUtilsController auc = new AmisUtilsController();
													//Variable used in the Cbs Tool to know which country is used
													final String country =  CCBS.COUNTRY_NAMES.get(0);

                                                   final String countryWelcomeMessage = "  " + BabelFish.print().welcome() + " <b>" + country + "</b>";

													CCBS.DATA_SOURCE_NAMES.clear();


													//The following (getAllCustomDatasets and getGroupPermission4Resource) should be in a new function and referenced when a country (i.e. a user group) has been clicked on from the getCountryCombo (user group),
													//Find All Custom Datasets
													AMISServiceEntry.getInstance().getAllCustomDatasets(new AsyncCallback<Map<Long, AMISCodesModelData>>() {
														public void onSuccess(Map<Long, AMISCodesModelData> customDatasets) {
															for(final Map.Entry<Long, AMISCodesModelData> customDatasetEntry: customDatasets.entrySet())
															{
																boolean ris = auc.isDatasourceForCountry();
																if(!ris)
																{
																	final long resourceId = customDatasetEntry.getKey();
																	final AMISCodesModelData dataSourceModel = customDatasetEntry.getValue();
																	final String dataSourceCode = dataSourceModel.getCode();

																	System.out.println("Class:AmisLogInController Function: getAllCustomDatasets********resourceId "+resourceId);
																	System.out.println("Class:AmisLogInController Function: getAllCustomDatasets********dataSourceModel.getCode() "+dataSourceModel.getCode()+ " dataSourceModel.getLabel()"+ dataSourceModel.getLabel());
																	System.out.println("Class:AmisLogInController Function: getAllCustomDatasets********country "+country);
																	//Check the group permissions for each custom dataset resource			
																	if((dataSourceCode!=null)&&(dataSourceCode.contains(country)))
																	{
																		//Check the group permissions for each custom dataset resource			
																		UserServiceEntry.getInstance().getGroupPermission4Resource(country, resourceId, new AsyncCallback<Map<String, Boolean>>() {
																			public void onSuccess(Map<String, Boolean> permissionMap) {

																				//Check if all the 4 permission types (DELETE, READ, WRITE, DOWNLOAD) are set to true, if so then this dataset belongs to the group
																				if(permissionMap.get("DELETE") && permissionMap.get("READ") && permissionMap.get("WRITE") && permissionMap.get("DOWNLOAD")) {
																					System.out.println("Group: "+country+" : permission values for dataset code '"+dataSourceCode+"' = [DELETE:"+ permissionMap.get("DELETE") +" | READ:" + permissionMap.get("READ") +" | WRITE:" + permissionMap.get("WRITE") +" | DOWNLOAD:" + permissionMap.get("DOWNLOAD")+ "]");
																					//The AMISCodesModelData (dataset code and dataset title) i.e. dataSourceModel would then be added to the DATA SOURCE Combo
																					//e.g. amisInput.getDataSourceCombo.getStore.add(model)
																					CCBS.DATA_SOURCE_NAMES.add(new String(dataSourceModel.getLabel()));
																					CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining));
																					auc.setDatasourceForCountry(true);
																				}

																				// Re-load the existing Current View and add Welcome message
																				if(currentSelectedView.equals(AMISCurrentView.INPUT) && !username.getValue().equals(CCBS.AMIS_SECRETARIAT)) {
																					AMISMenuController.openViewAgent(currentSelectedView, mainMenu);

																					if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
																						RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));

																					RootPanel.get("USER_LOGIN_WELCOME").add(buildWelcomeMessage(countryWelcomeMessage));
																				}
                                                                            }

																			public void onFailure(Throwable caught) {
																				Info.display(BabelFish.print().generalServerProblem(), caught
																						.getLocalizedMessage());
																			}
																		});
																	}
																}																
															}

															//dataSourceListBox.getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));

															//amisInput.getPanel().getLayout().layout();
														}

														public void onFailure(Throwable caught) {
															Info.display(BabelFish.print().generalServerProblem(), caught.getLocalizedMessage());
														}
													});
												}

												System.out.println("---- counter "+counter  + " | "+userGroupModel.getCode()+ " | " + userGroups.size());
												
												// Re-load ONCE the existing Current View and add Welcome message
												if(counter == userGroups.size()) {
                                                    String welcome = "";

													if(!currentSelectedView.equals(AMISCurrentView.INPUT)) {
                                                        AMISMenuController.openViewAgent(currentSelectedView, mainMenu);

                                                        if((username.getValue()!= null)&&!(username.getValue().equals(CCBS.AMIS_SECRETARIAT)))  {
                                                            String country =  CCBS.COUNTRY_NAMES.get(0);
                                                            welcome = "  " + BabelFish.print().welcome() + " <b>" + country + "</b>";
                                                        } else if(username.getValue().equals(CCBS.AMIS_SECRETARIAT)) {
                                                            welcome = welcomeMessage;
                                                        }

													}	
													else if(currentSelectedView.equals(AMISCurrentView.INPUT) && username.getValue().equals(CCBS.AMIS_SECRETARIAT)) {
														AMISMenuController.openViewAgent(currentSelectedView, mainMenu);

                                                        welcome = welcomeMessage;
													}

                                                    if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
                                                        RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));

                                                       RootPanel.get("USER_LOGIN_WELCOME").add(buildWelcomeMessage(welcome));

												}

											}

											public void onFailure(Throwable arg0) {
												arg0.printStackTrace();
											}
										});

									} catch (Exception e) {
										e.printStackTrace();
									}	
								}
							}
						}

						public void onFailure(Throwable caught) {
							Info.display(BabelFish.print().generalServerProblem(), caught.getLocalizedMessage());
						}
					});
				} else {
					String message = vo.getResponseMessage();
					if (vo.getResponseMessage().equals("UsernameNotFoundException"))
						message = BabelFish.print().UsernameNotFoundException();
					if (vo.getResponseMessage().equals("BadCredentialsException"))
						message = BabelFish.print().BadCredentialsException();
					if (vo.getResponseMessage().equals("AuthenticationException"))
						message = BabelFish.print().AuthenticationException();
					if (vo.getResponseMessage().equals("DisabledException"))
						message =  BabelFish.print().UserGroupDisabledException();;

					/*	ClickHtml html = new ClickHtml();
						html.setId("message");
						html.setHtml("<div class='login_welcome'>"+message + " <b>[click to cancel]</b></div>");
						html.setStyleAttribute("cursor", "pointer");
						html.addListener(Events.OnClick, removeLogInMessage());
*/
						
						Component errorMessageComponent = amisLoginRegisterPanel.errorMessagePanel.getItemByItemId("ERROR_MESSAGE");
    					
						if(errorMessageComponent!=null){
							Html html = (Html)errorMessageComponent;
							amisLoginRegisterPanel.errorMessagePanel.remove(html);
							
						}
						
						Html error = new Html("<div class='login_welcome'>"+message + "</div>");
						error.setId("ERROR_MESSAGE");
								
						amisLoginRegisterPanel.errorMessagePanel.add(error);
						amisLoginRegisterPanel.errorMessagePanel.getLayout().layout();
						//amisLoginPanel.panel.getLayout().layout();
						
						/*if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
							RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));

						RootPanel.get("USER_LOGIN_WELCOME").add(html);*/

						//amisInput.getPanel().add(html);
						//amisInput.getPanel().getLayout().layout();

				}						
			}
			public void onFailure(Throwable e) {
				FenixAlert.error(BabelFish.print().error(), e.getMessage());
			}
		});
	}
	
	public static void populateComboBox(final String usernameValue, final AMISInput amisInput)
	{
		//Find Groups the user belongs to
		//AMISServiceImpl findGroupsOfUser should be updated so that the user groups are translated to the appropriate
		//country code and label to fill the country combo 
		AMISServiceEntry.getInstance().findGroupsOfUser(usernameValue, new AsyncCallback<List<AMISCodesModelData>>() {
			public void onSuccess(List<AMISCodesModelData> userGroups) {
				CCBS.COUNTRY_NAMES.clear();
				CCBS.COUNTRY_CODES.clear();
				if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
				{
					amisInput.getCountryListBox().getStore().removeAll();
					//amisInput.getDataSourceListBox().getStore().removeAll();
				}
				final int iData=0;
//								amisInput.getCountryListBox().getStore().add(new AMISCodesModelData("", ""));
				for(final AMISCodesModelData userGroupModel: userGroups)
				{
					if(!userGroupModel.getLabel().equalsIgnoreCase("ALL_USER_GROUP"))
					{
						System.out.println("Class: AMISController findGroupsOfUser : userGroup Label = "+userGroupModel.getLabel());
						System.out.println("Class: AMISController findGroupsOfUser : userGroup Code = "+userGroupModel.getCode());
						
//						amisInput.getCountryListBox().getStore().add(userGroupModel);
//						amisInput.getPanel().getLayout().layout();
						
						AMISQueryVO qvo = new AMISQueryVO();
						qvo.setOutput(AMISConstants.CODING_SYSTEM.toString());
						qvo.setCountryName(userGroupModel.getLabel());
						qvo.setTypeOfOutput(AMISConstants.AMIS_COUNTRY_CROP_COUNT.toString());
						
						//1. amisInput.getCountryCombo.getStore.clear();(or removeaLL)
						//2. amisInput.getCountryCombo.getStore.add(userGroupModel)
						//3. amisInput.getPanel().getLayout().layout(); and remove from line 515

						try {
							AMISServiceEntry.getInstance().askAMIS(qvo, new AsyncCallback<AMISResultVO>() {
								
								@SuppressWarnings("unchecked")
								public void onSuccess(AMISResultVO vo) {
									
//										CCBS.COUNTRY_NAMES.clear();
//										CCBS.COUNTRY_CODES.clear();
									
									int J=0;
									for(AMISCodesModelData topcode : vo.getCodes()) {
									//	System.out.println("Class; CCBSParameterPanel  Function: loadGaulCountries Text: topcode.getLabel() "+topcode.getLabel());
										
										CCBS.COUNTRY_NAMES.add(topcode.getLabel());
										CCBS.COUNTRY_CODES.add(topcode.getCode());	
										if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
										{
											amisInput.getCountryListBox().getStore().add(new AMISCodesModelData(topcode.getCode(), topcode.getLabel()));
										}
										//iData++;
										//store.add(topcode);
										J++;
									}
									
									System.out.println("Class: AMISController findGroupsOfUser : username = "+usernameValue);
								//	amisInput.getCountryListBox().getStore().removeAll();
								if((usernameValue!= null)&&(usernameValue.equals(CCBS.AMIS_SECRETARIAT)))
								{
									System.out.println("Class: AMISController findGroupsOfUser : if((usernameValue!= null)&&(username.equals(CCBS.AMIS_SECRETARIAT)))");
								
//									for (int iCountry = 0; iCountry < CCBS.COUNTRY_NAMES.size(); iCountry++)
//									{
//										//countryListBox.addItem(CCBS.COUNTRY_NAMES.get(iCountry));
//										amisInput.getCountryListBox().getStore().add(new AMISCodesModelData(CCBS.COUNTRY_CODES.get(iCountry), CCBS.COUNTRY_NAMES.get(iCountry)));
//									}
//									for(int i=0; i< CCBS.COUNTRY_NAMES.size();i++)
//									{
//										System.out.println("********CCBS.COUNTRY_NAMES "+CCBS.COUNTRY_NAMES.get(i));
//									}
//									for(int i=0; i< CCBS.COUNTRY_CODES.size();i++)
//									{
//										System.out.println("********CCBS.COUNTRY_CODES "+CCBS.COUNTRY_CODES.get(i));
//									}
									//iData++;
									amisInput.setSelectCountryAndDatasourceInputPage();
								}
								else
								{
									final AmisUtilsController auc = new AmisUtilsController();
									System.out.println("Class: AMISController findGroupsOfUser : else((usernameValue!= null)&&(username.equals(CCBS.AMIS_SECRETARIAT)))");
									amisInput.getCountryLabel().setHtml("<div class='input_panel_text'><b>"+CCBS.COUNTRY_NAMES.get(0) +"</b></div>");
									for(int i=0; i< CCBS.COUNTRY_NAMES.size();i++)
									{
										System.out.println("********CCBS.COUNTRY_NAMES "+CCBS.COUNTRY_NAMES.get(i));
									}
									for(int i=0; i< CCBS.COUNTRY_CODES.size();i++)
									{
										System.out.println("********CCBS.COUNTRY_CODES "+CCBS.COUNTRY_CODES.get(i));
									}
									//Variable used in the Cbs Tool to know which country is used
									final String country =  CCBS.COUNTRY_NAMES.get(0);
									//Variable used in the Cbs Tool to know which country is used
									amisInput.getCountrySelected().setLabel(country);
									int i=0;
									for(i=0; i<CCBS.COUNTRY_NAMES.size(); i++)
									{
										if(CCBS.COUNTRY_NAMES.get(i).equals(country))
										{
											break;
										}
									}
									amisInput.getCountrySelected().setCode(CCBS.COUNTRY_CODES.get(i));
									amisInput.setSelectCountryTextInputPage(country);
									//Fill the data source listbox
									
									CCBS.DATA_SOURCE_NAMES.clear();
									//CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining + " (Training Set)"));
									amisInput.getDataSourceListBox().getStore().removeAll();
									//dataSourceListBox.getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));
								//4. The following (getAllCustomDatasets and getGroupPermission4Resource) should be in a new function and referenced when a country (i.e. a user group) has been clicked on from the getCountryCombo (user group),
								//Find All Custom Datasets
								AMISServiceEntry.getInstance().getAllCustomDatasets(new AsyncCallback<Map<Long, AMISCodesModelData>>() {
									public void onSuccess(Map<Long, AMISCodesModelData> customDatasets) {
										for(final Map.Entry<Long, AMISCodesModelData> customDatasetEntry: customDatasets.entrySet())
										{
											boolean ris = auc.isDatasourceForCountry();
											if(!ris)
											{
												final long resourceId = customDatasetEntry.getKey();
												final AMISCodesModelData dataSourceModel = customDatasetEntry.getValue();
												final String dataSourceCode = dataSourceModel.getCode();

												System.out.println("Class:AmisController Function: getAllCustomDatasets********resourceId "+resourceId);
												System.out.println("Class:AmisController Function: getAllCustomDatasets********dataSourceModel.getCode() "+dataSourceModel.getCode()+ " dataSourceModel.getLabel()"+ dataSourceModel.getLabel());
												System.out.println("Class:AmisController Function: getAllCustomDatasets********country "+country);
												//Check the group permissions for each custom dataset resource			
												if((dataSourceCode!=null)&&(dataSourceCode.contains(country)))
												{
												//Check the group permissions for each custom dataset resource			
												UserServiceEntry.getInstance().getGroupPermission4Resource(country, resourceId, new AsyncCallback<Map<String, Boolean>>() {
													public void onSuccess(Map<String, Boolean> permissionMap) {

														//Check if all the 4 permission types (DELETE, READ, WRITE, DOWNLOAD) are set to true, if so then this dataset belongs to the group
														if(permissionMap.get("DELETE") && permissionMap.get("READ") && permissionMap.get("WRITE") && permissionMap.get("DOWNLOAD")) {
															System.out.println("Group: "+country+" : permission values for dataset code '"+dataSourceCode+"' = [DELETE:"+ permissionMap.get("DELETE") +" | READ:" + permissionMap.get("READ") +" | WRITE:" + permissionMap.get("WRITE") +" | DOWNLOAD:" + permissionMap.get("DOWNLOAD")+ "]");
															//The AMISCodesModelData (dataset code and dataset title) i.e. dataSourceModel would then be added to the DATA SOURCE Combo
															//e.g. amisInput.getDataSourceCombo.getStore.add(model)
															//amisInput.getPanel().getLayout().layout();
															CCBS.DATA_SOURCE_NAMES.add(new String(dataSourceModel.getLabel()));
															CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining));
															amisInput.getDataSourceListBox().getStore().add(dataSourceModel);
															auc.setDatasourceForCountry(true);
														}
													}

													public void onFailure(Throwable caught) {
														Info.display(BabelFish.print().generalServerProblem(), caught
																.getLocalizedMessage());
													}
												});
												}
											}											
										}
										amisInput.getDataSourceListBox().getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));
										amisInput.getPanel().getLayout().layout();
									}

									public void onFailure(Throwable caught) {
										Info.display(BabelFish.print().generalServerProblem(), caught.getLocalizedMessage());
									}
								});
								}														
									
								//	Html selectFromComboBox = new Html("<div class='input_panel_text'><b>"+CCBS.COUNTRY_NAMES.get(0) +"</b></div>");
//									for(int i=0; i< amisInput.getCountryListBox().getStore().getCount();i++)
//									{
//										System.out.println("********Country label "+amisInput.getCountryListBox().getStore().getAt(i).getLabel());
//										System.out.println("********Country code "+amisInput.getCountryListBox().getStore().getAt(i).getCode());
//									}
									
																					}
																				
								public void onFailure(Throwable arg0) {
					
								}
							});
							
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				amisInput.getPanel().getLayout().layout();
			}

			public void onFailure(Throwable e) {
				FenixAlert.error(BabelFish.print().error(), e.getMessage());
			}
		});
	}
	
	
	
	public static SelectionChangedListener<AMISCodesModelData> countryComboListener(final ComboBox<AMISCodesModelData> countryListBox, final ComboBox<AMISCodesModelData> dataSourceListBox, final AMISInput amisInput) {
		return new SelectionChangedListener<AMISCodesModelData>() {
			public void selectionChanged(SelectionChangedEvent<AMISCodesModelData> se) {
				if((se != null)&&(se.getSelectedItem()!=null))
				{
					final AmisUtilsController auc = new AmisUtilsController();
					dataSourceListBox.setValue(null);
					final String country =  se.getSelectedItem().getLabel();
					//Variable used in the Cbs Tool to know which country is used
					amisInput.getCountrySelected().setLabel(country);
					int i=0;
					for(i=0; i<CCBS.COUNTRY_NAMES.size(); i++)
					{
						if(CCBS.COUNTRY_NAMES.get(i).equals(country))
						{
							break;
						}
					}
					System.out.println("Class:AmisController Function: countryComboListener********CCBS.COUNTRY_NAMES.size()"+CCBS.COUNTRY_NAMES.size());
					System.out.println("Class:AmisController Function: countryComboListener********CCBS.COUNTRY_CODES.size()"+CCBS.COUNTRY_CODES.size());
					for(int y=0; y< CCBS.COUNTRY_CODES.size();y++)
					{
						System.out.println("******** y= "+y+" CCBS.COUNTRY_CODES "+CCBS.COUNTRY_CODES.get(y) + " CCBS.COUNTRY_NAMES "+ CCBS.COUNTRY_NAMES.get(y));
					}
					System.out.println("Class:AmisController Function: countryComboListener********i "+i+" CCBS.COUNTRY_CODES.get(i) "+CCBS.COUNTRY_CODES.get(i));
					amisInput.getCountrySelected().setCode(CCBS.COUNTRY_CODES.get(i));
					
					CCBS.DATA_SOURCE_NAMES.clear();
					CCBS.DATA_SOURCE_NAMES.add(new String(CCBS.elementForTraining));
					dataSourceListBox.getStore().removeAll();
					dataSourceListBox.getStore().add(new AMISCodesModelData(CCBS.elementForTraining, CCBS.elementForTraining));
				//4. The following (getAllCustomDatasets and getGroupPermission4Resource) should be in a new function and referenced when a country (i.e. a user group) has been clicked on from the getCountryCombo (user group),
				//Find All Custom Datasets
				AMISServiceEntry.getInstance().getAllCustomDatasets(new AsyncCallback<Map<Long, AMISCodesModelData>>() {
					public void onSuccess(Map<Long, AMISCodesModelData> customDatasets) {
						for(final Map.Entry<Long, AMISCodesModelData> customDatasetEntry: customDatasets.entrySet())
					//	for(final Map.Entry<Long, AMISCodesModelData> customDatasetEntry: customDatasets.entrySet())
						{							
							boolean ris = auc.isDatasourceForCountry();
							if(!ris)
							{
								final long resourceId = customDatasetEntry.getKey();
								final AMISCodesModelData dataSourceModel = customDatasetEntry.getValue();
								final String dataSourceCode = dataSourceModel.getCode();
								System.out.println("Class:AmisController Function: getAllCustomDatasets********resourceId "+resourceId);
								System.out.println("Class:AmisController Function: getAllCustomDatasets********dataSourceModel.getCode() "+dataSourceModel.getCode()+ " dataSourceModel.getLabel()"+ dataSourceModel.getLabel());
								System.out.println("Class:AmisController Function: getAllCustomDatasets********country "+country);
								//Check the group permissions for each custom dataset resource			
								if((dataSourceCode!=null)&&(dataSourceCode.contains(country)))
								{
									UserServiceEntry.getInstance().getGroupPermission4Resource(country, resourceId, new AsyncCallback<Map<String, Boolean>>() {
										public void onSuccess(Map<String, Boolean> permissionMap) {

											//Check if all the 4 permission types (DELETE, READ, WRITE, DOWNLOAD) are set to true, if so then this dataset belongs to the group
											if(permissionMap.get("DELETE") && permissionMap.get("READ") && permissionMap.get("WRITE") && permissionMap.get("DOWNLOAD")) {
												System.out.println("Group: "+country+" : permission values for dataset code '"+dataSourceCode+"' = [DELETE:"+ permissionMap.get("DELETE") +" | READ:" + permissionMap.get("READ") +" | WRITE:" + permissionMap.get("WRITE") +" | DOWNLOAD:" + permissionMap.get("DOWNLOAD")+ "]");
												//The AMISCodesModelData (dataset code and dataset title) i.e. dataSourceModel would then be added to the DATA SOURCE Combo
												//e.g. amisInput.getDataSourceCombo.getStore.add(model)
												//amisInput.getPanel().getLayout().layout();
												CCBS.DATA_SOURCE_NAMES.add(new String(dataSourceModel.getLabel()));
												dataSourceListBox.getStore().add(dataSourceModel);
												auc.setDatasourceForCountry(true);
											}
										}

										public void onFailure(Throwable caught) {
											Info.display(BabelFish.print().generalServerProblem(), caught
													.getLocalizedMessage());
										}
									});
								}	
							}																		
						}
						amisInput.getPanel().getLayout().layout();
					}

					public void onFailure(Throwable caught) {
						Info.display(BabelFish.print().generalServerProblem(), caught.getLocalizedMessage());
					}
				});
				}

				
				
				
				
				
				
				
				
				
//				if((se != null)&&(se.getSelectedItem()!=null))
//				{
//					formData[1] = se.getSelectedItem().getLabel();
//					//To calculate the index
//					commodityListBox.setValue(null);
//					int indexCountryName=0;
//					String countryCode= "";
//					for(String countryName:CCBS.COUNTRY_NAMES)
//					{
//						if(countryName.equalsIgnoreCase(formData[1]))
//						{
//							break;
//						}
//						indexCountryName++;
//					}
//					if(indexCountryName<CCBS.COUNTRY_CODES.size())
//					{
//						countryCode = CCBS.COUNTRY_CODES.get(indexCountryName);
//					}
//					String selectionType = "AMIS_PRODUCTS";
//					loadDataForListBox(formData[1], selectionType, countryCode, formData[0]);
//					
////					String gaulName = gaulCode;
////					System.out.println("countryListBoxListener gaulCode= "+gaulCode);
////					System.out.println("countryListBoxListener gaulName= "+gaulName);
////					load(gaulCode+"", gaulName);
//				}
			//	else unload();
			}
		};
	}

	public static SelectionChangedListener<AMISCodesModelData> dataSourceComboListener(final ComboBox<AMISCodesModelData> dataSourceListBox, final HorizontalPanel hpGo, final AMISInput amisInput/*final Html to, final Html start*/) {
		return new SelectionChangedListener<AMISCodesModelData>() {
			public void selectionChanged(SelectionChangedEvent<AMISCodesModelData> se) {
				if((se != null)&&(se.getSelectedItem()!=null))
					{
					//This variable is used by the Cbs Tool to know which datasource is used.
						amisInput.getDataSourceSelected().setLabel(se.getSelectedItem().getLabel());
						amisInput.getDataSourceSelected().setCode(se.getSelectedItem().getLabel());
						Component component = hpGo.getItem(0);
						if(component != null)
						{
							ClickHtml go = (ClickHtml)component;
							if(go!= null)
							{
								go.enable();
							}
						}
						
//						List<Component> componentList =hpGo.getItems();
//						for(Component component:componentList)
//						{
//							if(component != null)
//							{
//								ClickHtml go = (ClickHtml)component;
//								if(go!= null)
//								{
//									go.enable();
//								}
//							}
//						}
					}
				
//				if((se != null)&&(se.getSelectedItem()!=null))
//				{
////					countryListBox.getStore().removeAll();
////					commodityListBox.getStore().removeAll();
////					countryListBox.getStore().removeAll();
////					monthListBox.getStore().removeAll();
////					monthListBox.getStore().add(new Month(""));
////					monthListBox.getStore().add(TestMonth.getMonth());
//					String dataSource = se.getSelectedItem().getLabel();
//					String monthBefore = "";
//					countryListBox.setValue(null);
//					commodityListBox.setValue(null);
//					monthListBox.setValue(null);
//					//to.setText("");
//					to.setHtml("");
//					for(int i = 0; i< formData.length; i++)
//					{
//						formData[i] = "";
//					}
//					formData[0] = dataSource;
//				//	lc.remove(lc.getWidget(11));
//				//	lc.remove(lc.getWidget(12));
//					//start.setHtml("<div class='ccbs-StartMonthLabel'></div>");
//					start.setHtml("<div class='ccbs-MYLabel'></div>");
//				//	to.setHtml("<div class='ccbs-MYLabel'></div>");
//					if(formData[0].equals(elementForTraining))
//					{
//						PagePanelController.showInfoPopup();
////						lc.remove(lc.getWidget(11));
////						start.setHtml("<div class='ccbs-StartMonthLabel'></div>");
////						to.setHtml("<div class='ccbs-MYLabel'></div>");
//					//	lc.insert(start, 11);
//						String selectionType = "AMIS_COUNTRIES";
//						loadDataForListBox(dataSource, selectionType, "", formData[0]);
//						FieldSet fieldset = (FieldSet)lc.getWidget(11);
//						int i = 0;
//						for(Component component:fieldset.getItems())
//						{
//							if(i<1)
//							{
//								RadioGroup radiogroup = (RadioGroup)component;
//								radiogroup.get(0).disable();
//								radiogroup.get(1).disable();
//								break;
//							}
//							i++;
//						}
//						//RadioGroup radiogroup = (RadioGroup)fieldset.getItemByItemId(itemId);
//						//RadioGroup radiogroup = (RadioGroup)lc.getWidget(13);
//			
//						ComboBox<AMISCodesModelData> combo = (ComboBox<AMISCodesModelData>)lc.getWidget(12);
//						combo.disable();
//					}
//					else
//					{
//						//lc.remove(lc.getWidget(11));
//						//lc.insert(monthListBox,11);
//						String selectionType = "AMIS_COUNTRIES";
//						loadDataForListBox(dataSource, selectionType, "", formData[0]);
//						FieldSet fieldset = (FieldSet)lc.getWidget(11);
//						int i = 0;
//						for(Component component:fieldset.getItems())
//						{
//							if(i<1)
//							{
//								RadioGroup radiogroup = (RadioGroup)component;
//								radiogroup.get(0).enable();
//								radiogroup.get(1).enable();
//								break;
//							}
//							i++;
//						}
//					}
//				}
//				else 
//				{
//					unload();
//				}
			}
		};
	}

	
	
	private static Listener<BaseEvent> removeLogInMessage(final AMISInput amisInput) {
		return new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent ce) {
//				Component component = amisInput.getPanel().getItemByItemId("message");
//				if(component!=null) {
//					ClickHtml message = (ClickHtml)component;
//					amisInput.getPanel().remove(message);
//					amisInput.getPanel().getLayout().layout();
//				}	
				
				int i = 0;
				while(i<RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount())		
				{
					Widget w = RootPanel.get("USER_LOGIN_WELCOME").getWidget(i);
					ClickHtml html = (ClickHtml)w;
					if((html != null)&&(html.getId()!=null)&&(html.getId().equals("message")))
					{
						RootPanel.get("USER_LOGIN_WELCOME").getWidget(i).removeFromParent();
						break;
					}
					i++;
				}				
			}
		};
	}
		
		public static Listener<ComponentEvent> buildLoginPanel(final AMISMainMenu mainMenu) {
			return new Listener<ComponentEvent>() {
				public void handleEvent(ComponentEvent ce) {
					
				System.out.println("-------- buildLoginPanel currentSelectedView "+currentSelectedView);
				    
				//AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
					//AMISHistoryController.setHistoryAnchor(currentSelectedView);
				
				AMISHome.hideHomeVisibility();
				
				if (RootPanel.get("MAIN_CONTENT").getWidgetCount() > 0)
				      RootPanel.get("MAIN_CONTENT").remove(RootPanel.get("MAIN_CONTENT").getWidget(0));
				RootPanel.get("OLAP_IFRAME").setVisible(false);
				RootPanel.get("OLAP_IFRAME_NO_ELEMENTS").setVisible(false);
				RootPanel.get("COMPARE_NOTES").setVisible(false);
                RootPanel.get("SUB_MENU_DOWNLOAD").setVisible(false);
//                    if (RootPanel.get("SUB_MENU_DOWNLOAD").getWidgetCount() > 0)
//                        RootPanel.get("SUB_MENU_DOWNLOAD").remove(RootPanel.get("SUB_MENU_DOWNLOAD").getWidget(0));
					
				//	if(currentSelectedView.equals(ADAMCurrentVIEW.HOME)) 
					//	AMISHome.hideHomeVisibility();
					
					
						
					
				    RootPanel.get("MAIN_CONTENT").add(new AMISLoginRegisterPanel(mainMenu).build());
					
				}
			};
		}
		
		public static Listener<ComponentEvent> logOut(final AMISMainMenu mainMenu) {
			return new Listener<ComponentEvent>() {
				public void handleEvent(ComponentEvent ce) {
					FenixUser.giveAnonymousRole();
					AMISLoginPanel.getAmisUserParameters().setUsername("");
					AMISLoginPanel.getAmisUserParameters().setPwd("");
					
                    System.out.println("LOG OUT ========= ");

                    //Remove INPUT DATA from Menu
                    mainMenu.removeInputMenuItem();

					if (RootPanel.get("CLOSE_CBS_TOOL").getWidgetCount() > 0)
						RootPanel.get("CLOSE_CBS_TOOL").remove(RootPanel.get("CLOSE_CBS_TOOL").getWidget(0));
					
					if (RootPanel.get("MAIN_CONTENT").getWidgetCount() > 0)
						RootPanel.get("MAIN_CONTENT").remove(RootPanel.get("MAIN_CONTENT").getWidget(0));
					
					//Remove compare sub menu
					if ( RootPanel.get("SUB_MENU_COMPARE").getWidgetCount() > 0 ) {
					    RootPanel.get("SUB_MENU_COMPARE").remove(RootPanel.get("SUB_MENU_COMPARE").getWidget(0));
					}

                    //Remove download sub menu
                    if ( RootPanel.get("SUB_MENU_DOWNLOAD").getWidgetCount() > 0 ) {
                        RootPanel.get("SUB_MENU_DOWNLOAD").remove(RootPanel.get("SUB_MENU_DOWNLOAD").getWidget(0));
                    }

					//Remove Welcome
					if (RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount() > 0)
						RootPanel.get("USER_LOGIN_WELCOME").remove(RootPanel.get("USER_LOGIN_WELCOME").getWidget(0));
					
					//Re-Set Log Out to Log In
					buildLogMessage(mainMenu, buildLoginPanel(mainMenu), "Log In");

                    if(currentSelectedView.toString() == "DOWNLOAD"){
                        //currentSelectedView = AMISCurrentView.getCurrentView("DOWNLOAD");
                        AMISHistoryController.setHistoryAnchor(AMISCurrentView.DOWNLOAD);
                        //AMISMenuController.openViewAgent(AMISCurrentView.DOWNLOAD, mainMenu);
                    }

                    if(currentSelectedView.equals(AMISCurrentView.INPUT))
                        AMISMenuController.openViewAgent(AMISCurrentView.HOME, mainMenu);
                    else
					    AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
					
				}
			};
		}
		
		
		
		public static Listener<ComponentEvent> cancel(final AMISMainMenu mainMenu) {
			return new Listener<ComponentEvent>() {
				public void handleEvent(ComponentEvent ce) {
					if (RootPanel.get("MAIN_CONTENT").getWidgetCount() > 0)
						RootPanel.get("MAIN_CONTENT").remove(RootPanel.get("MAIN_CONTENT").getWidget(0));
					
					AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
					
				}
			};
		}
			
		public static void buildLogMessageOriginal(final AMISMainMenu mainMenu,  Listener<ComponentEvent> listener, String text) {
			
			Element p = RootPanel.get("USER_LOGIN").getElement();
			 ClickHtml clickHtml = new ClickHtml();
			 clickHtml.setId("USER_LOGIN");
			 clickHtml.setHtml(text);
			 clickHtml.setStyleName("login");
			 clickHtml.addListener(Events.OnClick, listener);
			 p.getParentElement().replaceChild(clickHtml.getElement(), p);
			 clickHtml.attach();
			 
			  //AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
			 
			
		} 
		
public static void buildLogMessageOld(final AMISMainMenu mainMenu,  Listener<ComponentEvent> listener, String text) {
	//USER_LOGIN_MENU		
	
//			Element p = RootPanel.get("USER_LOGIN").getElement();
			Element p = RootPanel.get("USER_LOGIN_MENU").getElement();
			 ClickHtml clickHtml = new ClickHtml();
			 clickHtml.setId("USER_LOGIN");
			 clickHtml.setHtml(text);
			 clickHtml.setStyleName("login");
			 clickHtml.setStyleAttribute("backgroundColor", "#DC3018");
			 clickHtml.addListener(Events.OnClick, listener);
		//	 clickHtml.setHeight(35);
		//	 clickHtml.setWidth(70);
			// p.getParentElement().setStyleAttribute("backgroundColor", "#DC3018");
			 p.getParentElement().replaceChild(clickHtml.getElement(), p);
			
			 clickHtml.attach();
			 
			  //AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
		} 


public static void buildLogMessage(final AMISMainMenu mainMenu,  Listener<ComponentEvent> listener, String text) {
	//USER_LOGIN_MENU		
			
		try {
			if (RootPanel.get("USER_LOGIN_MENU").getWidgetCount() > 0) {
			        RootPanel.get("USER_LOGIN_MENU").remove(RootPanel.get("USER_LOGIN_MENU").getWidget(0));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
//		HorizontalPanel p = new HorizontalPanel();
//		 p.setStyleAttribute("valign", "TOP");
//		 p.setStyleAttribute("align", "RIGHT");
		
//		p.setData("base-style-name", "tab");
//		p.addStyleName("tab");
			 ClickHtml clickHtml = new ClickHtml();
			 clickHtml.setId("USER_LOGIN");
			 clickHtml.setHtml(text);
			 clickHtml.setStyleName("login");
			 clickHtml.setStyleAttribute("backgroundColor", "#DC3018");
//			 clickHtml.setStyleAttribute("valign", "TOP");
//			 clickHtml.setStyleAttribute("align", "RIGHT");
			 //clickHtml.setStyleAttribute("margin-right", "0px");
			 clickHtml.addListener(Events.OnClick, listener);
			 //clickHtml.setWidth(65);
			 //clickHtml.setWidth(45);
			//clickHtml.setWidth(50);
			clickHtml.setWidth(50);
//			 clickHtml.setHeight(22);
			 clickHtml.setHeight(24);
//				if(text.equals("Log In"))
//				{
//					clickHtml.setWidth(width);
//				}
//				else if(text.equals("Log Out"))
//				{
//					clickHtml.setWidth(86);
//				}
		//	 p.add(clickHtml);
//			 mainMenu.getMenuPanelLogIn().add(clickHtml);
//			 mainMenu.getPanelLogIn().add(mainMenu.getMenuPanelLogIn());
//			 RootPanel.get("USER_LOGIN_MENU").add(mainMenu.getPanelLogIn());
			 RootPanel.get("USER_LOGIN_MENU").add(clickHtml);
			 
		} 
		
		public static void buildLogMessage3(final AMISMainMenu mainMenu,  Listener<ComponentEvent> listener, String text) {
			
			//Element p = RootPanel.get("MENU_TABLE").get("USER_LOGIN").getElement();
//			Element p = RootPanel.get("USER_LOGIN").getElement();
//			Html h = new Html(text);
//			h.addListener(Events.OnClick, listener);
			try {
				 if (RootPanel.get("USER_LOGIN2").getWidgetCount() > 0) {
		                RootPanel.get("USER_LOGIN2").remove(RootPanel.get("USER_LOGIN2").getWidget(0));
		        }
				    RootPanel.get("USER_LOGIN2").add(new Html("Pippo"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		   
//			Element p = RootPanel.get("USER_LOGIN").add(h)
//			Element p = Document.get().getElementById("USER_LOGIN");
			
			Html clickHtml = new Html();
			
			
		//	ClickHtml clickHtml = new ClickHtml();
			 clickHtml.setId("USER_LOGIN");
			 clickHtml.setHtml(text);
			 clickHtml.setStyleName("login");
			 clickHtml.addListener(Events.OnClick, listener);
		//	 p.getParentElement().replaceChild(clickHtml.getElement(), p);
//			 RootPanel.get("USER_LOGIN").remove(0);
//			 
//			 RootPanel.get("USER_LOGIN").add(clickHtml);
			
		//	 p.getParentElement().appendChild(clickHtml.getElement());
			//p.appendChild((new NodeclickHtml);
			// Document.get().getElementById("USER_LOGIN").appendChild();
			// clickHtml.
		//	 clickHtml.attach();
			 
			  //AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
			 
			
		} 
		
public static void buildLogMessageInMainMenu(final AMISMainMenu mainMenu,  Listener<ComponentEvent> listener, String text) {
			
	Element panel = RootPanel.get("MENU").getElement();
	//panel.getId();
	System.out.println(" ***************************************************TEST!!!!!!buildLogMessageInMainMenu  panel.getId()=  "+panel.getId());
	System.out.println(" ***************************************************TEST!!!!!!buildLogMessageInMainMenu = title "+mainMenu.getMenuPanel().getElement().getTitle());
	for(int i= 0; i<panel.getChildCount(); i++)
	{
		Node n = panel.getChild(i);
		System.out.println(" ***************************************************TEST!!!!!!buildLogMessageInMainMenu  i = "+i+" n.getNodeName()=  "+n.getNodeName());
		System.out.println(" ***************************************************TEST!!!!!!buildLogMessageInMainMenu  i = "+i+" n.getNodeValue()=  "+n.getNodeValue());
	}
	//USER_LOGIN_MENU
//			Element p = RootPanel.get("USER_LOGIN").getElement();
			Element p = RootPanel.get("USER_LOGIN_MENU").getElement();
			 ClickHtml clickHtml = new ClickHtml();
			 clickHtml.setId("USER_LOGIN");
			 clickHtml.setHtml(text);
			 clickHtml.setStyleName("login");
			 clickHtml.addListener(Events.OnClick, listener);
			 p.getParentElement().replaceChild(clickHtml.getElement(), p);
			 clickHtml.attach();
			 
			  //AMISMenuController.openViewAgent(currentSelectedView, mainMenu);
			 
			
		} 
		
		public static HorizontalPanel buildWelcomeMessage(String text) {
			HorizontalPanel vp = new HorizontalPanel();
			HorizontalPanel panel = new HorizontalPanel();
			panel.addStyleName("login_welcome_panel");
			panel.setStyleAttribute("backgroundColor", "#FFFFFF");
			panel.setSpacing(10);
			vp.setStyleAttribute("backgroundColor", "#FFFFFF");
			HTML html = new HTML();
			html.setHTML("<div class='login_welcome'>" + text + "</div>");
			panel.add(html);
			vp.setHorizontalAlign(HorizontalAlignment.RIGHT);
			vp.setTableWidth("100%");
			vp.add(panel);
			return vp;
		} 
		
		private static Listener<BaseEvent> removeLogInMessage() {
			return new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent ce) {
					
					int i = 0;
					while(i<RootPanel.get("USER_LOGIN_WELCOME").getWidgetCount())		
					{
						Widget w = RootPanel.get("USER_LOGIN_WELCOME").getWidget(i);
						ClickHtml html = (ClickHtml)w;
						if((html != null)&&(html.getId()!=null)&&(html.getId().equals("message")))
						{
							RootPanel.get("USER_LOGIN_WELCOME").getWidget(i).removeFromParent();
							break;
						}
						i++;
					}				
				}
			};
		}
}

