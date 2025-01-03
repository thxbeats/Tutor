const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');



module.exports = {
    entry: './src/main/react/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js',
    },
    module: {
        rules: [
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                },

            },
        ],
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './src/main/react/index.html',
        }),
    ],
    devServer: {
        static: {
            directory: path.join(__dirname, 'public'), // Убедитесь, что путь указан правильно
        },
        proxy: {
            '/api': {
                target: 'https://localhost:8443', // HTTPS
                secure: false // Для локальной разработки можно отключить проверку SSL сертификата
            }
        }
    }

};
