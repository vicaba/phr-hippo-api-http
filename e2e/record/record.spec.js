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

    it("should successfully create a record and retrieve it", async () => {
        let createReq = await request(baseUrl)
            .post("/record")
            .send(record)
            .set("Accept", "application/json")
            .set("Content-Type", "application/json")

        expect(createReq.statusCode).to.be.equal(200)
        expect(createReq.body.header.patientId).to.be.equal(record.header.patientId)
        expect(createReq.body.header.headline).to.be.equal(record.header.headline)
        expect(createReq.body.body.body).to.be.equal(record.body.body)

        let getReq = await request(baseUrl)
            .get("/record/" + createReq.body.header.id)
            .set("Accept", "application/json")
            .set("Content-Type", "application/json")

        expect(getReq.statusCode).to.be.equal(200)
        expect(getReq.body.toString()).to.be.equal(createReq.body.toString())


    });

})


