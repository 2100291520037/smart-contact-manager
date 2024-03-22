package com.exampleSmartStudent.demo8.controler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.exampleSmartStudent.demo8.Dao.ContactRepository;
import com.exampleSmartStudent.demo8.Dao.UserRepository;
import com.exampleSmartStudent.demo8.entity.Contact;
import com.exampleSmartStudent.demo8.entity.User;
import com.exampleSmartStudent.demo8.helper.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserControler {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	// adding common data
	@ModelAttribute
	public  void addCommonData(Model model,Principal principal){
		String username = principal.getName();
		System.out.println(username+"  milgya Email mil gya");
		Optional<User> user=userRepository.getUserByUserName(username);
		System.out.println(user.toString());
		model.addAttribute("user",user.orElse(null));
		model.addAttribute("contact",new Contact());

	}
	//home dashboard
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal)
	{

		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	@GetMapping("/add-contact")
	public String openAddContactFrom(Model model)
	{
		model.addAttribute("title","Add Contact");

		return "normal/add_contact_form";
	}
    //processing add contact form
    @PostMapping("/process-contact")
    public String process(@ModelAttribute Contact contact, @RequestParam("profile_image")MultipartFile file, Principal principal,Model model)
    {
		try {
			System.out.println("mil gya");
			System.out.println(contact);
			String name=principal.getName();
			Optional<User> user = this.userRepository.getUserByUserName(name);
			//procesinf file
			if(file.isEmpty())
			{
               contact.setImage("contact.png");
			}
			else {
				contact.setImage(file.getOriginalFilename());
				File file1=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(file1.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			}
			User user1= user.get();
			contact.setUser(user1);
			user1.getContacts().add(contact);
			this.userRepository.save(user1);
			System.out.println("add");
            Message message = new Message("Successfully Add contact And more !!","alert-success");
            model.addAttribute("message",message);



		}
		catch (Exception e)
		{
            model.addAttribute("message", new Message("Someting Error try Again!!","alert-danger"));
			System.out.println( "error"+ e.getMessage());
			e.printStackTrace();
		}



        return "normal/add_contact_form";
    }
	//show contact handler
	//@GetMapping("/show-contacts")
	@GetMapping("/show-contacts/{page}")
	public String showContact(@PathVariable("page") Integer page, Model m,Principal principal)
	{
		m.addAttribute("title","Show ALl Contacts ");
	//	Optional<User> user =this.userRepository.getUserByUserName()
		String username =principal.getName();
		Optional<User> user =this.userRepository.getUserByUserName(username);
		Pageable pageable =PageRequest.of(page,5);
		Page<Contact> contactList=this.contactRepository.findContactsByUser(user.get().getId(),pageable);
		//for(Contact c : contactList) System.out.println(c);
		m.addAttribute("contacts",contactList);
		m.addAttribute("current",page);
		m.addAttribute("totalPages",contactList.getTotalPages());
		return "normal/show_contacts";
	}
	@RequestMapping("/{cid}/contact")
	public String showContactDetail(@PathVariable("cid") Integer cid,Model model,Principal principal)
	{
		System.out.println("cid "+cid);
		Optional<Contact> contact = this.contactRepository.findById(cid);
      String name=  principal.getName();
        Optional<User> user = this.userRepository.getUserByUserName(name);
        if(user.get().getId()==contact.get().getUser().getId()) {
			model.addAttribute("model", contact.get());
			model.addAttribute("title",contact.get().getName());
		}


		return "normal/contact-detail";
	}
    //delete
    @GetMapping("/delete/{cid}")
    public String deleteContact(@PathVariable("cid") Integer cid, @RequestParam("profile_image")MultipartFile file,Model model) throws IOException {
        Optional<Contact> contact =this.contactRepository.findById(cid);
		System.out.println("yes walla "+contact.get().getUser().getId());
        //contact.get().setUser(null);
		File deletefile=new ClassPathResource("static/img").getFile();
		File file2 = new File(deletefile,contact.get().getImage());
		file2.delete();
        Optional<User> user = this.userRepository.findById(contact.get().getUser().getId());
		   user.get().getContacts().remove(contact.get());
		   this.userRepository.save(user.get());
		   this.contactRepository.delete(contact.get());
// Remove the association
//		user.getContacts().remove(contact);
//		contact.setUser(null);
//
//		userService.saveUser(user);
//		contactService.deleteContact(contact);
        // check
       // this.contactRepository.deleteByCid(contact.get().getCid());
		Message message = new Message("Successfully Delete contact  !!","alert-success");

        model.addAttribute("message",message);
        return "redirect:/user/show-contacts/0";
    }
    // opeb update form handler
    @PostMapping("/update-contact/{cid}")
    public String updatefrom(@PathVariable("cid") Integer cid, Model model)
    {
        model.addAttribute("title","Update Contact");
        Optional<Contact> contact = this.contactRepository.findById(cid);
        model.addAttribute("contact",contact.get());
        return "normal/update_form";
    }
	// update handler
	@PostMapping("/process-update")
	public  String updateHander(@ModelAttribute Contact contact, @RequestParam("profile_image")MultipartFile file,Model model,Principal principal){
		try {
			Optional<Contact> oldcontact = this.contactRepository.findById(contact.getCid());
			//image
			if(!file.isEmpty()){
				// delete old photo
				File deletefile=new ClassPathResource("static/img").getFile();
				File file2 = new File(deletefile,oldcontact.get().getImage());
				file2.delete();

               // update
				contact.setImage(file.getOriginalFilename());
				File file1=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(file1.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

			}
			else {
				contact.setImage(oldcontact.get().getImage());

			}
			Optional<User> user =this.userRepository.getUserByUserName(principal.getName());
			contact.setUser(user.get());
          this.contactRepository.save(contact);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "redirect:/user/"+contact.getCid()+"/contact";
	}

}
///user/252/contact
