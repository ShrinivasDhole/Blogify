import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { FaUserCircle } from "react-icons/fa";
import { MdKeyboardArrowDown } from "react-icons/md";

const Navbar = () => {
  const [categoryDropdown, setCategoryDropdown] = useState(false);
  const [profileDropdown, setProfileDropdown] = useState(false);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/categories")
      .then(response => setCategories(response.data))
      .catch(error => console.error("Error fetching categories:", error));
  }, []);

  return (
    <nav className="bg-blue-600 text-white py-4 shadow-md">
      <div className="container mx-auto flex justify-between items-center px-6">
        <Link to="/" className="text-2xl font-bold">Blogify</Link>

        <div className="flex space-x-6">
          <Link to="/" className="hover:text-gray-300">Home</Link>
          <Link to="/create" className="hover:text-gray-300">Create Blog</Link>

          {/* Categories Dropdown */}
          <div className="relative">
            <button
              onClick={() => setCategoryDropdown(!categoryDropdown)}
              className="flex items-center space-x-2 hover:text-gray-300"
            >
              <span>Categories</span>
              <MdKeyboardArrowDown />
            </button>

            {categoryDropdown && (
              <div className="absolute top-10 left-0 w-48 bg-white text-gray-900 shadow-md rounded-md">
                {categories.map(category => (
                  <Link key={category.id} to={`/category/${category.name}`} className="block px-4 py-2 hover:bg-gray-200">
                    {category.name}
                  </Link>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* Profile Dropdown */}
        <div className="relative">
          <button onClick={() => setProfileDropdown(!profileDropdown)} className="flex items-center space-x-2 hover:text-gray-300">
            <FaUserCircle className="text-2xl" />
            <MdKeyboardArrowDown />
          </button>

          {profileDropdown && (
            <div className="absolute top-10 right-0 w-48 bg-white text-gray-900 shadow-md rounded-md">
              <Link to="/profile" className="block px-4 py-2 hover:bg-gray-200">Dashboard</Link>
              <Link to="/settings" className="block px-4 py-2 hover:bg-gray-200">Settings</Link>
              <button className="block w-full text-left px-4 py-2 hover:bg-gray-200">Logout</button>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
