package ca.montreal.mesmorize.configuration;


/**
 * Enum class that defines the authorities that can be assigned to a user
 */
public enum Authority {Admin, User}

// The difference between roles and authorities
// hasRole('member') == hasAuthority('ROLE_member')