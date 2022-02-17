import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';

class BookEdit extends Component {


    emptyItem = {
        name: '',
        author: {"author": '', "id": null},
        genre: []
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const book = await (await fetch(`/books/${this.props.match.params.id}`)).json();
            this.setState({
                item: book
            });
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        console.log(value)
        console.log(name)
        if (name === 'author') {
            console.log(item)
            item[name].author = value
        } else if (name === 'genre') {
            item[name] = []
            value.split(",").forEach(function (val, index, array) {
                item[name][index] = {"id": null, "genre": val};
            })
        } else {
            item[name] = value
        }
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/books' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/books');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit book' : 'Add book'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="author">Author</Label>
                        <Input type="text" name="author" id="author" value={item.author.author || ''}
                               onChange={this.handleChange} autoComplete="author"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="genre">Genres</Label>
                        <Input type="text" name="genre" id="genre"
                               value={item.genre.map(value => value.genre).join(",") || ''}
                               onChange={this.handleChange} autoComplete="genre"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(BookEdit);