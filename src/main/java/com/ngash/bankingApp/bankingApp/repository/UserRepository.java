package com.ngash.bankingApp.bankingApp.repository;


import com.ngash.bankingApp.bankingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    public Boolean existsByEmail(String email);
    public Boolean existsByAccountNumber(String accountNumber);
    public User findByAccountNumber(String accountNumber);
}


 <p:panelGrid columns="1" style=" width: 100%; padding: 20px" >
                        <p:commandButton  value="new" onclick="PF('dlg3').show()" style="margin:auto; width: 5%; position: absolute; right: 0; left:0; margin-top: -1rem"/>
                    </p:panelGrid>
                    
                    
                    <p:dialog header="New Connection" widgetVar="dlg3" minHeight="40" width="550" showEffect="fade" closeOnEscape="true">
                        <p:panelGrid columns="2" style="width: 100%">
                            <h:outputLabel for="hostaddress1" value="Host Address "/>
                            <p:inputText id="hostaddress1"  value="#{newDbConnectionBean.address}"/>
                            <h:outputLabel for="name1" value="Database Name: "/>
                            <p:inputText id="name1"  value="#{newDbConnectionBean.databaseName}"/>
                            <h:outputLabel for="username1" value="User Name: "/>
                            <p:inputText id="username1" value="#{newDbConnectionBean.userName}" />
                            <h:outputLabel for="password1" value="Password: "/>
                            <p:password id="password1" value="#{newDbConnectionBean.password}" />
                            <h:outputLabel for="option1" value="Connection Type: "/>
                            <p:selectOneMenu id="option1" value="#{newDbConnectionBean.connectionType}" label="Select One">
                                <f:selectItems  value="#{newDbConnectionBean.connectionTypes}" />
                            </p:selectOneMenu>
                        </p:panelGrid>

                        <p:panelGrid  style=" width: 70%; margin: auto; padding-top: 20px; padding-bottom: 20px ">
                            <p:commandButton value="save connection" style="margin-left: -5rem" action="#{newDbConnectionBean.saveData()}" ajax="false" update="growl"/>
                        </p:panelGrid>
                    </p:dialog>
