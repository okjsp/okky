import grails.test.AbstractCliTestCase

class GcmTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGcm() {

        execute(["gcm"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
