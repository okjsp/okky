import grails.test.AbstractCliTestCase

class UpdatePictureTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUpdatePicture() {

        execute(["update-picture"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
