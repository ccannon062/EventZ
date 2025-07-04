# EventZ - Event Management System

A full-stack event management application built with React and Spring Boot that allows users to browse events, register for them, and manage their registrations.

## 🚀 Features

### User Features
- **Browse Events**: View all upcoming events with detailed information
- **Event Details**: See comprehensive event information including organizer details
- **User Registration**: Create an account and log in securely
- **Event Registration**: Register for events with automatic waitlist management
- **User Dashboard**: View and manage your event registrations
- **Cancel Registrations**: Remove yourself from events you can no longer attend

### Organizer/Admin Features
- **Create Events**: Add new events with full details
- **Event Management**: Update and delete events
- **Role-based Access**: Different permissions for users, organizers, and admins

### Technical Features
- **Responsive Design**: Works on desktop and mobile devices
- **Real-time Updates**: Dynamic UI updates without page refreshes
- **JWT Authentication**: Secure token-based authentication
- **API Documentation**: Swagger/OpenAPI documentation
- **Input Validation**: Client and server-side validation

## 🛠️ Tech Stack

### Frontend
- **React 18** - User interface library
- **React Router** - Client-side routing
- **CSS3** - Styling and responsive design
- **Fetch API** - HTTP client for API calls

### Backend
- **Spring Boot 3.5** - Java framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database abstraction
- **JWT** - Token-based authentication
- **H2 Database** - In-memory database (development)
- **PostgreSQL** - Production database
- **Swagger/OpenAPI** - API documentation

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17 or higher**
- **Node.js 16 or higher**
- **npm** (comes with Node.js)
- **Git**

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd eventz
```

### 2. Backend Setup (Spring Boot)
```bash
# Navigate to the backend directory
cd EventZ

# Make the Maven wrapper executable (Linux/Mac)
chmod +x mvnw

# Install dependencies and start the backend
./mvnw spring-boot:run

# For Windows
mvnw.cmd spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup (React)
```bash
# Navigate to the frontend directory
cd eventz-frontend

# Install dependencies
npm install

# Start the development server
npm start
```

The frontend will start on `http://localhost:3000`

## 🗄️ Database Configuration

### Development (H2 Database)
The application uses H2 in-memory database for development. Access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:eventdb`
- Username: `sa`
- Password: (leave empty)

### Production (PostgreSQL)
For production, update `application-prod.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/eventz_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 👥 Default Users

The application comes with pre-configured test users:

| Email | Password | Role | Permissions |
|-------|----------|------|-------------|
| alice@eventz.com | password123 | ADMIN | Full access |
| bob@eventz.com | password123 | EVENT_ORGANIZER | Create/manage events |
| carol@eventz.com | password123 | EVENT_ORGANIZER | Create/manage events |

## 🔑 API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/users/register` - User registration

### Events
- `GET /api/events` - Get all events
- `GET /api/events/{id}` - Get event by ID
- `POST /api/events` - Create new event (authenticated)
- `PUT /api/events/update/{id}` - Update event (authenticated)
- `DELETE /api/events/{id}` - Delete event (authenticated)
- `GET /api/events/upcoming` - Get upcoming events
- `GET /api/events/date-range` - Get events by date range

### Registrations
- `POST /api/registrations` - Register for event
- `DELETE /api/registrations/{id}` - Cancel registration
- `GET /api/registrations/user/{userId}` - Get user's registrations
- `GET /api/registrations/event/{eventId}` - Get event registrations
- `GET /api/registrations/check` - Check registration status

### API Documentation
Access the interactive API documentation at: `http://localhost:8080/swagger-ui/index.html`

## 🎯 Usage Guide

### For Regular Users
1. **Browse Events**: Visit the homepage to see all available events
2. **View Details**: Click on any event card to see full details
3. **Register**: Log in and click "Register for Event" on event details page
4. **Manage Registrations**: Use "My Dashboard" to view and cancel registrations

### For Event Organizers
1. **Create Events**: Log in and click "Create Event" in the navigation
2. **Fill Details**: Provide event name, description, location, dates, and capacity
3. **Manage**: View created events and manage registrations

## 🔐 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Role-based Access Control**: Different permissions for different user types
- **CORS Configuration**: Properly configured for frontend-backend communication
- **Input Validation**: Server-side validation for all inputs
- **Password Encoding**: BCrypt password hashing

## 🚀 Deployment

### Frontend Deployment (Netlify/Vercel)
```bash
# Build the React app
cd eventz-frontend
npm run build

# Deploy the 'build' folder to your hosting service
```

### Backend Deployment (Heroku/Railway)
1. Create a production profile in `application-prod.properties`
2. Configure PostgreSQL database
3. Set environment variables for database credentials
4. Deploy using your preferred platform

## 🧪 Testing

### Backend Testing
```bash
cd EventZ
./mvnw test
```

### Frontend Testing
```bash
cd eventz-frontend
npm test
```

## 📁 Project Structure

```
eventz/
├── EventZ/                     # Spring Boot backend
│   ├── src/main/java/com/schedule/eventz/
│   │   ├── controller/         # REST controllers
│   │   ├── models/            # Entity models
│   │   ├── repositories/      # Data repositories
│   │   ├── security/          # Security configuration
│   │   ├── service/           # Business logic
│   │   └── config/            # Configuration classes
│   ├── src/main/resources/
│   │   ├── application-dev.properties
│   │   ├── application-prod.properties
│   │   └── dummy-events.json
│   └── pom.xml               # Maven dependencies
│
├── eventz-frontend/          # React frontend
│   ├── public/              # Static files
│   ├── src/
│   │   ├── components/      # React components
│   │   ├── styles/         # CSS files
│   │   ├── App.js          # Main app component
│   │   └── index.js        # Entry point
│   ├── package.json        # npm dependencies
│   └── .gitignore
│
└── README.md               # This file
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🐛 Known Issues

- Registration status checking may not update immediately after registration
- Email notifications are configured but disabled by default
- Date validation could be improved for edge cases

## 🔮 Future Enhancements

- [ ] Email notifications for registrations
- [ ] Event categories and filtering
- [ ] Calendar view for events
- [ ] Event photo uploads
- [ ] Social media integration
- [ ] Mobile app development
- [ ] Advanced search functionality
- [ ] Event reviews and ratings

## 📞 Support

If you encounter any issues or have questions, please:
1. Check the existing issues on GitHub
2. Create a new issue with detailed description
3. Contact the development team

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the powerful UI library
- All contributors and testers

---

**Made with ❤️ using React and Spring Boot**
