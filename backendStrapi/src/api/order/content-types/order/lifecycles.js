module.exports = {
    afterCreate(event) {
        const { result } = event;

        const nodemailer = require('nodemailer');
        enviar = async () => {
            const config = {
                host: 'smtp.gmail.com',
                port: 587,
                auth: {
                    user: 'sistemaoperativoindependiente@gmail.com',
                    pass: 'biyvxblpoqqutson'
                }
            }
            const mensaje = {
                from: 'sistemaoperativoindependiente@gmail.com',
                to: 'sistemaoperativoindependiente@gmail.com',
                subject: 'Reporte de compra',
                text: '',
                html: `
                <div style="padding:0px 20px 20px; width: 240px; font-family: sans-serif; border: 1px solid #E5E7E9; box-shadow: 1px 1px 10px #999;">
        <div style="background-color: #fff; width: 100%; border-bottom: 1px dashed #888;">
            <center>
                <h3 style="width:180px;color: #566573; border: 2px solid #566573; display: flex;">
                    <span style="border: 1px solid #888; width: 100%; margin: 2px; padding: 1px;">Tienda Muebles</span>
                </h3>
            </center>
        </div>
        <br>
        <div style="color:#888; font-size: .9em; border-bottom: 1px dashed #888;">
            <table>
                <tr>
                    <td style="font-weight: bold;">
                        Cliente:
                    </td>
                    <td>${result.ClientName}</td>
                </tr>
                <tr>
                    <td style="font-weight: bold;">
                        Fecha:
                    </td>
                    <td>${result.createdAt}</td>
                </tr>
                <tr>
                    <td style="font-weight: bold;">
                        Orden #:
                    </td>
                    <td>${result.id}</td>
                </tr>
            </table>
            <br>
        </div>
        <br>
        <div>
            <strong>Monto Total:</strong> L. ${result.Amount}
        </div>
    </div>
                `
            }
            try {
                const transport = nodemailer.createTransport(config);
                const info = await transport.sendMail(mensaje);
                console.log(info)
            } catch (error) {
                console.warn(error)
            }
        }
        enviar();
    },
    afterUpdate(event) {
        const { result, params } = event;
    },
}