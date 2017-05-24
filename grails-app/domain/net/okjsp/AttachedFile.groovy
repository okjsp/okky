package net.okjsp

class AttachedFile {

    String name
    String orgName
    String mimeType
    Integer byteSize
    Integer width
    Integer height
    AttachedFileType type

    static constraints = {
        width nullable: true
        height nullable: true
    }

    static mapping = {}
}
