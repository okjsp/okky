package net.okjsp

class Follow implements Serializable {

    private static final long serialVersionUID = 1

    Avatar following
    Avatar follower

    Date dateCreated

    static constraints = {
    }

    static mapping = {
        id composite: ['follower', 'following']
        version false
    }
}
