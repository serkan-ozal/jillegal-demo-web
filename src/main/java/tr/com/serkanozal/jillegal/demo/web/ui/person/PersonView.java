/**
 * @author SERKAN OZAL
 *         
 *         E-Mail: <a href="mailto:serkanozal86@hotmail.com">serkanozal86@hotmail.com</a>
 *         GitHub: <a>https://github.com/serkan-ozal</a>
 */
 
package tr.com.serkanozal.jillegal.demo.web.ui.person;

import java.util.Date;

import org.springframework.util.StringUtils;

import tr.com.serkanozal.jillegal.demo.web.domain.Person;
import tr.com.serkanozal.jillegal.demo.web.service.PersonService;
import tr.com.serkanozal.jillegal.demo.web.util.SpringContextProvider;
import tr.com.serkanozal.jillegal.demo.web.util.UiUtil;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class PersonView extends VerticalLayout {

	private Panel pnlFetchPerson;
	private AbsoluteLayout lytFetchPerson;
	private Label lblFetchId;
	private TextField txtFetchId;
	private Button btnFetch;
	
	private Panel pnlPersonInformations;
	private FormLayout lytPersonInformations;
	private TextField txtId;
	private TextField txtUsername;
	private TextField txtFirstName;
	private TextField txtLastName;
	private DateField dtfBirthDate;
	private TextField txtAccountNo;
	private TextField txtDebt;
	private Button btnSave;
	
	public PersonView(SpringContextProvider springContextProvider) {
		final PersonService personService = springContextProvider.getBean(PersonService.class);
		
		VerticalLayout vl = new VerticalLayout();
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		pnlFetchPerson = new Panel();
		pnlFetchPerson.setCaption("Fetch Person");
		pnlFetchPerson.addStyleName(Reindeer.LAYOUT_BLUE);
		
		lytFetchPerson = new AbsoluteLayout();
		lytFetchPerson.addStyleName(Reindeer.LAYOUT_BLUE);
		lytFetchPerson.setWidth("100%");
		lytFetchPerson.setHeight("50px");
		
		lblFetchId = new Label("Person ID to Fetch: ");
		txtFetchId = new TextField();
		btnFetch = new Button("Fetch Person");
		
		btnFetch.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					String value = txtFetchId.getValue();
					if (StringUtils.isEmpty(value)) {
						UiUtil.showErrorMessage("ID is mandatory to fetch person informations");
					} 
					else {
						try {
							Long id = Long.parseLong(value);
							if (id < 0 || id > Person.MAX_PERSON_COUNT) {
								UiUtil.showErrorMessage("ID can be between 0 and " + Person.MAX_PERSON_COUNT);
							} 
							else {
								Person person = personService.get(id);
								if (person != null) {
									txtId.setValue(String.valueOf(person.getId()));
									txtUsername.setValue(person.getUsername());
									txtFirstName.setValue(person.getFirstName());
									txtLastName.setValue(person.getLastName());
									dtfBirthDate.setValue(new Date(person.getBirthDate()));
									txtAccountNo.setValue(String.valueOf(person.getAccountNo()));
									txtDebt.setValue(String.valueOf(person.getDebt()));
								}
								else {
									UiUtil.showWarningMessage("Person couldn't be found with ID " + id);
								}
							}
						} catch (NumberFormatException e) {
							UiUtil.showErrorMessage("ID must be number between 0 and " + Person.MAX_PERSON_COUNT);
						}
					}
				} 
				catch (Throwable t) {
					t.printStackTrace();
					UiUtil.showErrorMessage(t.getMessage());
				}
			}
		});
		
		lytFetchPerson.addComponent(lblFetchId, "top: 10px; left: 10px;");
		lytFetchPerson.addComponent(txtFetchId, "top: 10px; left: 130px;");
		lytFetchPerson.addComponent(btnFetch, "top: 10px; left: 300px");
		
		pnlFetchPerson.setContent(lytFetchPerson);
		
		/////////////////////////////////////////////////////////////////////////////////////////

		pnlPersonInformations = new Panel();
		pnlPersonInformations.setCaption("Person Informations");
		pnlPersonInformations.addStyleName(Reindeer.LAYOUT_BLUE);
		
		lytPersonInformations = new FormLayout();
		lytPersonInformations.addStyleName(Reindeer.LAYOUT_BLUE);
        lytPersonInformations.setSpacing(true);
		lytPersonInformations.setWidth("100%");
		lytPersonInformations.setHeight("300px");
		lytPersonInformations.setMargin(true);
		
		txtId = new TextField("ID: ");
		txtUsername = new TextField("Username: ");
		txtFirstName = new TextField("First Name: ");
		txtLastName = new TextField("Last name: ");
		dtfBirthDate = new DateField("Birth Date: ");
		txtAccountNo = new TextField("Account No: ");
		txtDebt = new TextField("Debt: ");
		btnSave = new Button("Save Person");
		
		dtfBirthDate.setWidth("135px");
		btnSave.setWidth("135px");
		
		btnSave.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					String idValue = txtId.getValue();
					String usernameValue = txtUsername.getValue();
					String firstNameValue = txtFirstName.getValue();
					String lastNameValue = txtLastName.getValue();
					Date birthDateValue = dtfBirthDate.getValue();
					String accountNoValue = txtAccountNo.getValue();
					String debtValue = txtDebt.getValue();
					
					if (StringUtils.isEmpty(idValue)) {
						UiUtil.showErrorMessage("ID cannot be empty");
						return;
					}

					if (StringUtils.isEmpty(usernameValue)) {
						UiUtil.showErrorMessage("Username cannot be empty");
						return;
					}
					
					if (StringUtils.isEmpty(firstNameValue)) {
						UiUtil.showErrorMessage("Firstname cannot be empty");
						return;
					}
					
					if (StringUtils.isEmpty(lastNameValue)) {
						UiUtil.showErrorMessage("Lastname cannot be empty");
						return;
					}
					
					if (birthDateValue == null) {
						UiUtil.showErrorMessage("Birth date cannot be empty");
					}
					
					if (StringUtils.isEmpty(accountNoValue)) {
						UiUtil.showErrorMessage("Account no cannot be empty");
						return;
					}
					
					if (StringUtils.isEmpty(debtValue)) {
						UiUtil.showErrorMessage("Debt cannot be empty");
						return;
					}
					
					Long id = 0L;
					Integer accountNo = 0;
					Double debt = 0.0;
					
					try {
						id = Long.parseLong(idValue);
						if (id < 0 || id > Person.MAX_PERSON_COUNT) {
							UiUtil.showErrorMessage("ID can be between 0 and " + Person.MAX_PERSON_COUNT);
						}	
					} 
					catch (NumberFormatException e) {
						UiUtil.showErrorMessage("ID must be number between 0 and " + Person.MAX_PERSON_COUNT);
						return;
					}	
					
					try {
						accountNo = Integer.parseInt(accountNoValue);
						if (accountNo <= 0) {
							UiUtil.showErrorMessage("Account no can be positive integer");
						}
					} 
					catch (NumberFormatException e) {
						UiUtil.showErrorMessage("Account no must be positive integer");
						return;
					}	
					
					try {
						debt = Double.parseDouble(debtValue);
						if (debt <= 0) {
							UiUtil.showErrorMessage("Debt can be positive double");
						}	
					} 
					catch (NumberFormatException e) {
						UiUtil.showErrorMessage("Debt must be positive double");
						return;
					}	
					
					Person person = personService.newPerson();
						
					person.setId(id);
					person.setUsername(usernameValue);
					person.setFirstName(firstNameValue);
					person.setLastName(lastNameValue);
					person.setBirthDate(birthDateValue);
					person.setAccountNo(accountNo);	
					person.setDebt(debt);	
					
					personService.save(person);
					
					txtUsername.setValue(person.getUsername());
					txtFirstName.setValue(person.getFirstName());
					txtLastName.setValue(person.getLastName());
					
					System.out.println(person);
					
					UiUtil.showInfoMessage("Person saved successfully");
				}
				catch (Throwable t) {
					t.printStackTrace();
					UiUtil.showErrorMessage(t.getMessage());
				}
			}
		});
		
		lytPersonInformations.addComponent(txtId);
		lytPersonInformations.addComponent(txtUsername);
		lytPersonInformations.addComponent(txtFirstName);
		lytPersonInformations.addComponent(txtLastName);
		lytPersonInformations.addComponent(dtfBirthDate);
		lytPersonInformations.addComponent(txtAccountNo);
		lytPersonInformations.addComponent(txtDebt);
		lytPersonInformations.addComponent(btnSave);

		pnlPersonInformations.setContent(lytPersonInformations);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		vl.addStyleName(Reindeer.LAYOUT_BLUE);
		vl.addComponent(pnlFetchPerson);
		vl.addComponent(pnlPersonInformations);
		
		vl.setMargin(true);
		vl.setSpacing(true);
		
		addComponent(vl);
		addStyleName(Reindeer.LAYOUT_BLUE);
		setMargin(false);
		setSpacing(false);
		setSizeFull();
	}
	
}
