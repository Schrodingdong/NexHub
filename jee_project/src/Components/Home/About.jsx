import React from 'react';
import './Home.css'
import image from '../../images/logo.avif';

const About = () => {
  return (
    <div className='body'>
      <div className='heading-about'>
        <h1>About Us</h1>
      </div>
      <div className='container'>
        <section className='about'>
          <div className='about-image'>
          <img src={image} alt='Learnify logo'/></div>
          <div className='title_about'>
            <h1>NexHub</h1>
            <p>Nexhub is a web application designed to facilitate the sharing of resources among users in the IT fields. With Learnify, users can upload, organize and share a variety of resources including articles, tutorials, videos, and courses on topics such as programming, web development, data science, and cybersecurity. Users can also search and discover resources shared by others in the community, rate and review resources, and create and join groups with like-minded individuals. Learnify provides a platform for IT enthusiasts to come together and collaborate, share knowledge, and accelerate their learning journey. Whether you're a beginner or an experienced professional, Learnify can help you expand your knowledge and stay up to date with the latest trends and technologies in the IT world.</p>
          </div>
        </section>
      </div>
    </div>
  );
};

export default About;
