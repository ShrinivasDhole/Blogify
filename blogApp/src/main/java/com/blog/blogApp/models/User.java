package com.blog.blogApp.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = { "posts", "comments", "likes" })
public class User implements UserDetails {

	private static final long serialVersionUID = -7939290560694615298L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String bio;

	@Column(name = "Role", nullable = false)
	private String role;

	@Lob
	@Column(nullable = true)
	private byte[] profileImage; // Changed to byte array to store image as BLOB

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(this.role);
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public String getRole() {
		// TODO Auto-generated method stub
		return role;
	}
}
