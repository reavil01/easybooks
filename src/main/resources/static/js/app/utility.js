
function readFormToJson() {
    const data = $('form')
        .serializeArray()
        .reduce((data, kv) => ({...data, [kv.name]: kv.value}), {});
    return JSON.stringify(data)
}