package com.mohsinkerai.adminlte.users;

import com.mohsinkerai.adminlte.base.BaseEntity;
import com.mohsinkerai.adminlte.users.authority.MyAuthority;
import com.mohsinkerai.adminlte.users.authority.MyAuthoritySwitchDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class MyUser extends BaseEntity implements UserDetails {

  private static final long serialVersionUID = -3945671920756923566L;

  @Column(unique = true, nullable = false)
  private String username;
  private String password;
  private Boolean enabled = true;
  private Boolean locked = false;
  private Boolean credentialsExpired = false;
  private Boolean accountExpired = false;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
    name = "my_user_authority",
    joinColumns = @JoinColumn(name = "my_authority_id"),
    inverseJoinColumns = @JoinColumn(name = "my_user_id")
  )
  private List<MyAuthority> myAuthorities;

  @Transient
  @Getter
  private List<MyAuthoritySwitchDto> authoritySwitchDtos;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return myAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !accountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !credentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled == null ? true : enabled;
  }

  @Override
  public String toString() {
    return new ToStringCreator(this)
      .append("Username", username)
      .append("Password", "[Protected]")
      .append("Enabled", enabled)
      .append("Credentials Expired", credentialsExpired)
      .append("Account Locked", locked)
      .append("Account Expired", accountExpired)
//      .append("Authorities", myAuthorities)
      .toString();
  }

  public void apply(UserDetails userDetails) {
    this.username = userDetails.getUsername();
    this.password = userDetails.getPassword();
    this.enabled = userDetails.isEnabled();
    this.locked = !userDetails.isAccountNonLocked();
    this.credentialsExpired = !userDetails.isCredentialsNonExpired();
    this.accountExpired = !userDetails.isAccountNonExpired();
    this.myAuthorities = userDetails.getAuthorities().stream().map(this::apply).collect(Collectors.toList());
  }

  private MyAuthority apply(GrantedAuthority authority) {
    // Fetch from repo to avoid duplication
    return new MyAuthority(authority.getAuthority());
  }
}
