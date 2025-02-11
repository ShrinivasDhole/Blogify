import { Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import CreateBlogPage from "./components/CreateBlogPage";
import EditBlogPage from "./components/EditBlogPage";
import CategoryBlogsPage from "./components/CategoryBlogsPage";
import PostPage from "./components/PostPage";
import Navbar from "./components/Navbar";

function App() {
  return (
    <div className="App">
      <Navbar/>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/create" element={<CreateBlogPage />} />
        <Route path="/edit/:id" element={<EditBlogPage />} />
        <Route path="/category/:category" element={<CategoryBlogsPage />} />
        <Route path="/post/:id" element={<PostPage />} />
      </Routes>
      </div>
  );
}

export default App;
