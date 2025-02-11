import { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const HomePage = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/posts')
      .then(response => setPosts(response.data))
      .catch(error => console.error('Error fetching posts:', error));
  }, []);

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-4xl font-bold mb-6 text-center text-gray-900">Blog Posts</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-8">
        {posts.map(post => (
          <div key={post.id} className="bg-white rounded-lg shadow-lg overflow-hidden transition-transform transform hover:scale-105 hover:shadow-xl">
            <img
              src={post.imageUrl || 'default-image.jpg'}
              alt={post.title}
              className="w-full h-48 object-cover transition-transform duration-500 ease-in-out transform hover:scale-110"
            />
            <div className="p-6">
              <h2 className="text-2xl font-semibold text-gray-800 hover:text-blue-600 transition-colors mb-4">{post.title}</h2>
              <p className="text-gray-600 text-base">{post.content.substring(0, 150)}...</p>
              <Link to={`/post/${post.id}`} className="text-blue-500 hover:underline mt-4 inline-block">
                Read More
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default HomePage;
