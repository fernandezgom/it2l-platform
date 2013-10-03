package com.hibernate.dto;

// Generated 08-Mar-2013 17:55:24 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "italk2learn")
public class User implements java.io.Serializable {

	private int idUser;
	private String user;
	private Set<Sequence> sequences = new HashSet<Sequence>(0);

	public User() {
	}

	public User(int idUser) {
		this.idUser = idUser;
	}

	public User(int idUser, String user, Set<Sequence> sequences) {
		this.idUser = idUser;
		this.user = user;
		this.sequences = sequences;
	}

	@Id
	@Column(name = "id_user", unique = true, nullable = false)
	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	@Column(name = "user", length = 50)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Sequence> getSequences() {
		return this.sequences;
	}

	public void setSequences(Set<Sequence> sequences) {
		this.sequences = sequences;
	}

}
