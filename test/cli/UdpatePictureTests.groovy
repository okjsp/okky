import grails.test.AbstractCliTestCase

class UdpatePictureTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUdpatePicture() {

        execute(["udpate-picture"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
