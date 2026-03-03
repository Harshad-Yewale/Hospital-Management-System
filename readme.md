<h1>🏥 Hospital Management System (Backend)</h1>

<p>
A Spring Boot backend project built to demonstrate practical understanding of 
<strong>Spring Data JPA</strong>, entity relationships, transactional business logic, 
and clean layered architecture.
</p>

<p>
This project focuses on proper domain modeling and service-layer rule enforcement 
rather than simple CRUD operations.
</p>

<hr>

<h2>🚀 Tech Stack</h2>
<ul>
  <li>Java 17+</li>
  <li>Spring Boot</li>
  <li>Spring Data JPA</li>
  <li>Hibernate</li>
  <li>MySQL</li>
  <li>Jakarta Validation</li>
  <li>Lombok</li>
  <li>RESTful API Design</li>
</ul>

<hr>

<h2>🎯 Project Goal</h2>
<p>
The primary objective of this project was to move beyond basic CRUD and deeply understand:
</p>
<ul>
  <li>JPA relationship modeling</li>
  <li>Owning vs inverse side concepts</li>
  <li>Cascade and orphanRemoval behavior</li>
  <li>Transactional boundaries</li>
  <li>Enum-based domain modeling</li>
  <li>Service-layer business rule enforcement</li>
  <li>RESTful lifecycle design</li>
</ul>

<hr>

<h2>🏗 Architecture</h2>

<pre>
Controller → Service → Repository → Entity → Database
</pre>

<p><strong>Key Design Decisions:</strong></p>
<ul>
  <li>No business logic inside controllers</li>
  <li>DTO-based request/response handling</li>
  <li>Global exception handling</li>
  <li>Transactional service methods</li>
  <li>EnumType.STRING for domain safety</li>
  <li>Soft delete using status enum (for appointments)</li>
</ul>

<hr>

<h2>📦 Domain Modules</h2>

<h3>👤 Patient</h3>
<ul>
  <li>Basic patient details</li>
  <li>Linked to Insurance (ManyToOne)</li>
  <li>Linked to Appointment (OneToMany)</li>
</ul>

<h3>👨‍⚕ Doctor</h3>
<ul>
  <li>Doctor details</li>
  <li>Linked to Department (ManyToMany)</li>
  <li>Linked to Appointment (OneToMany)</li>
</ul>

<h3>📅 Appointment</h3>
<ul>
  <li>Lifecycle management (SCHEDULED → CANCELLED → COMPLETED)</li>
  <li>Double booking prevention</li>
  <li>Soft delete via status update</li>
</ul>

<h3>🏢 Department</h3>
<ul>
  <li>Many-to-Many relationship with Doctors</li>
  <li>Assign / Remove doctors</li>
</ul>

<h3>🛡 Insurance</h3>
<ul>
  <li>Linked to multiple patients</li>
  <li>Expiry validation logic</li>
  <li>Safe deletion with relationship handling</li>
</ul>

<hr>

<h2>🔥 Implemented Features</h2>

<h3>Appointment Management</h3>
<ul>
  <li>Create appointment</li>
  <li>Update appointment with validation</li>
  <li>Cancel appointment (soft delete)</li>
  <li>Complete appointment</li>
  <li>Prevent scheduling in the past</li>
  <li>Prevent double booking (service + DB level)</li>
  <li>Get appointments by patient and doctor</li>
</ul>

<h3>Doctor ↔ Department</h3>
<ul>
  <li>Assign doctor to department</li>
  <li>Remove doctor from department</li>
  <li>Get doctors in a department</li>
  <li>Get departments of a doctor</li>
</ul>

<h3>Patient ↔ Insurance</h3>
<ul>
  <li>Assign insurance to patient</li>
  <li>Remove insurance</li>
  <li>Get all patients under insurance</li>
  <li>Prevent assigning expired insurance</li>
</ul>

<hr>

<h2>🛠 Running the Project</h2>

<h3>1️⃣ Clone Repository</h3>
<pre>
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME
</pre>

<h3>2️⃣ Configure Database</h3>
<p>Update <code>application.properties</code>:</p>

<pre>
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
</pre>

<p>Create database in MySQL:</p>

<pre>
CREATE DATABASE hospital_db;
</pre>

<h3>3️⃣ Run Application</h3>
<pre>
mvn spring-boot:run
</pre>

<p>Server runs at:</p>
<pre>
http://localhost:8080
</pre>

<hr>

<h2>📌 Example Endpoints</h2>

<h3>Appointments</h3>
<ul>
  <li>POST /api/appointments</li>
  <li>PUT /api/appointments/{id}</li>
  <li>PATCH /api/appointments/{id}/cancel</li>
  <li>PATCH /api/appointments/{id}/complete</li>
</ul>

<h3>Doctors</h3>
<ul>
  <li>DELETE /api/doctors/{id}?replacementDoctorId=2</li>
</ul>

<h3>Departments</h3>
<ul>
  <li>PATCH /api/departments/{deptId}/assign/{doctorId}</li>
  <li>GET /api/departments/{id}/doctors</li>
</ul>

<h3>Insurance</h3>
<ul>
  <li>PATCH /api/patients/{id}/insurance/{insuranceId}</li>
  <li>GET /api/insurance/{id}/patients</li>
</ul>

<hr>

<h2>📈 Future Improvements</h2>
<ul>
  <li>Pagination & Sorting (Pageable)</li>
  <li>Fetch optimization (EntityGraph / Fetch Join)</li>
  <li>Spring Security (Role-based access)</li>
</ul>

<hr>

<h2>🧠 Learning Outcome</h2>
<p>
This project helped reinforce understanding of:
</p>
<ul>
  <li>JPA relationship ownership</li>
  <li>Transactional consistency</li>
  <li>Service-layer domain rule design</li>
  <li>Clean RESTful API structure</li>
</ul>

<hr>

<p><strong>Feedback and suggestions are welcome.</strong></p>