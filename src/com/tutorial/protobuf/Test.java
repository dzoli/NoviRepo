package com.tutorial.protobuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
 
import com.tutorial.protobuf.EmployeeProto.Department;
import com.tutorial.protobuf.EmployeeProto.Employee;
import com.tutorial.protobuf.EmployeeProto.Employee.Builder;

import person.AddressBookProto;
import person.AddressBookProto.AddressBook;
import person.AddressBookProto.Person;
import person.AddressBookProto.Person.PhoneNumber;
import person.AddressBookProto.Person.PhoneType;

public class Test {

	public static void main(String[] args) {

		// creating the employee
		Builder empbuild = EmployeeProto.Employee.newBuilder();
		empbuild.setFirstname("Novica");
		empbuild.setLastname("Bjelica");
		empbuild.setId(10);
		empbuild.setSalary(100000);
		
		Department dept1 = EmployeeProto.Department.newBuilder().setDeptid(1).setDeptname("Finance").build();
		Department dept2 = EmployeeProto.Department.newBuilder().setDeptid(2).setDeptname("Admin").build();
		empbuild.addDept(dept1);
		empbuild.addDept(dept2);

		Employee novica = empbuild.build();

		//...... addressBook{ person{ name,id,email,PhoneNumber{ number,type}}*} ......
		//person address has zero or more people-person
		person.AddressBookProto.AddressBook.Builder addressBookBuild = AddressBookProto.AddressBook.newBuilder();
				
		//people has email, id, name and [phoneNumber]=msg
		person.AddressBookProto.Person.Builder person = AddressBookProto.Person.newBuilder();
		person.setEmail("email");
		person.setId(1);
		person.setName("novica");
				
		PhoneNumber phone = 
				AddressBookProto.Person.PhoneNumber.newBuilder().setNumber("555-333").setType(PhoneType.HOME).build();
		person.addPhones(phone);
		person.build();
			
		addressBookBuild.addPeople(person);
		AddressBook addressBookNovica = addressBookBuild.build();
		try{
			System.out.println(" === Address Book before serialization === ");
			System.out.println(addressBookNovica);

			//write employeee
			FileOutputStream out1 = new FileOutputStream("Employee.txt");
			novica.writeTo(out1); //serialize and write to output
			out1.close();
			
			//read employee
			System.out.println(" === Employee message deserialized (paresd) from file === ");
			Employee empFromFile = Employee.parseFrom(new FileInputStream("Employee.txt"));
			System.out.println(empFromFile);

			//write address book
			System.out.println(" === Address Book message is serialized! and writed to AddressBook.txt file === ");
			FileOutputStream out2 = new FileOutputStream("AddressBook.txt");
			addressBookNovica.writeTo(out2);
			out2.close();

			//read address book
			System.out.println(" === Address Book message deserialized from AddressBook.txt file === ");
			AddressBook addressFromFile = AddressBook.parseFrom(new FileInputStream("AddressBook.txt"));
			System.out.println(addressFromFile);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
