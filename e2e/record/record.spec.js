// require("debug").enable("supertest*")

const request = require("supertest")
const expect = require("chai").expect
const describe = require("mocha").describe
const it = require("mocha").it
const record = require("./fixtures/record.json")

describe("Restful Record API tests", () => {
    const baseUrl = process.env.API_HTTP_URL || "http://localhost:8081"
    console.log(baseUrl)
    var recordId = undefined

/*    it("should successfully create a record", (done) => {
        request(baseUrl)
            .post("/record")
            .send(record)
            .set("Accept", "application/json")
            .set("Content-Type", "application/json")
            .end((err, res) => {
                // console.log(res.res.req)
                expect(res.statusCode).to.be.equal(200)
                if (err) done(err);
                done();
            });
    });*/
    // npm run test
    // Test passing
    it("should successfully get a record", (done) => {
        request(baseUrl)
            .get("/record/292a485f-a56a-4938-8f1a-bbbbbbbbbbb1" )
            .set("Accept", "application/json")
            .set("Content-Type", "application/json")
            .end((err, res) => {
                // console.log(res.res.req)
                expect(res.statusCode).to.be.equal(200)
                console.log(res.body)
                if (err) done(err);
                done();
            });
    });

})


