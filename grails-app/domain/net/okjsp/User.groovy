package net.okjsp

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = false
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false

    Date lastPasswordChanged = new Date()

    Date dateCreated
    Date lastUpdated

    Person person
    Avatar avatar

    String createIp
    String lastUpdateIp

    static hasMany = [loggedIns: LoggedIn, oAuthIDs: OAuthID]

	static transients = ['springSecurityService']

	static constraints = {
		username(blank: false, unique: true, size: 5..15, matches: /^[a-z0-9]*[a-z]+[a-z0-9]*$/, validator: {
            if(disAllowUsernameFilter(it)) return ['default.invalid.disallow.message']
        })
		password blank: false, minSize: 6, matches: /^.*(?=.*[0-9])(?=.*[a-z]).*$/
        person unique: true
        avatar unique: true
        enabled bindable: false
        accountExpired bindable: false
        accountLocked bindable: false
        passwordExpired bindable: false
        lastPasswordChanged bindable: false
        loggedIns bindable: false
        createIp bindable: false, nullable: true
        lastUpdateIp nullable: true, bindable: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

    static boolean disAllowUsernameFilter(username) {
        return ['root','administrator','administration','rootadmin','adminroot'
            ,'system','tomcatroot','sysadmin','sysroot','manager','management','manage'].contains(username)
    }

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
