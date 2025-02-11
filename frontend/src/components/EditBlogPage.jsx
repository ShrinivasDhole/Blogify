import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

const EditBlogPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [category, setCategory] = useState("");
  const [image, setImage] = useState(null);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/posts/${id}`)
      .then(response => {
        const post = response.data;
        setTitle(post.title);
        setContent(post.content);
        setCategory(post.category);
      })
      .catch(error => console.error("Error fetching post:", error));
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    formData.append("category", category);
    if (image) formData.append("image", image);

    try {
      await axios.put(`http://localhost:8080/api/posts/${id}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      navigate(`/post/${id}`);
    } catch (error) {
      console.error("Error updating blog:", error);
    }
  };

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-4xl font-bold mb-6 text-center">Edit Blog</h1>
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md">
        <div className="mb-4">
          <label htmlFor="title" className="block text-gray-700">Title</label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full p-2 mt-2 border rounded-md"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="content" className="block text-gray-700">Content</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full p-2 mt-2 border rounded-md"
            rows="5"
            required
          ></textarea>
        </div>
        <div className="mb-4">
          <label htmlFor="category" className="block text-gray-700">Category</label>
          <input
            type="text"
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            className="w-full p-2 mt-2 border rounded-md"
            required
          />
        </div>
        <div className="mb-4">
          <label htmlFor="image" className="block text-gray-700">Upload Image</label>
          <input
            type="file"
            id="image"
            onChange={(e) => setImage(e.target.files[0])}
            className="w-full p-2 mt-2 border rounded-md"
          />
        </div>
        <button
          type="submit"
          className="w-full py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-700"
        >
          Save Changes
        </button>
      </form>
    </div>
  );
};

export default EditBlogPage;
