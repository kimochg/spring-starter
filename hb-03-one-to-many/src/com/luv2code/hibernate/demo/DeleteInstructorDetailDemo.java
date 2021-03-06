package com.luv2code.hibernate.demo;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteInstructorDetailDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Instructor.class)
                                    .addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {

			// start a transaction
			session.beginTransaction();

			// get the instructor detail object
			int id = 3;

			InstructorDetail instructorDetail =
					session.get(InstructorDetail.class, id);

			// print the instructor detail
			System.out.println("instructor detail: " + instructorDetail);

			// print the associated instructor
			System.out.println("the associated instructor: " +
					instructorDetail.getInstructor());

			// remove the associated oject reference
            // break bi-directional link
			instructorDetail.getInstructor().setInstructorDetail(null);

			// now, let's delete the instructor detail
			session.delete(instructorDetail);

			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		} catch(Exception exc) {
		    exc.printStackTrace();
		} finally {

		    // handle session leak issue
			session.close();

			factory.close();
		}
	}

}
